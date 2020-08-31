package com.qrcodemall.controller;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.controller.vo.QrcodeBatchVO;
import com.qrcodemall.entity.*;
import com.qrcodemall.service.*;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * @Author: Peony
 * @Date: 2020/8/26 13:52
 */
@RestController
@RequestMapping("/qrcodeBatch")
public class QrcodeBatchController {

    @Autowired
    QrcodeBatchService qrcodeBatchService;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    QrcodeService qrcodeService;

    @Autowired
    UserAddressService userAddressService;

    @Autowired
    HttpSession session;

    //
    @GetMapping("/adminFind")
    @ApiOperation(value = "admin的不定参数查询")
    public Result<PageInfo<QrcodeBatchVO>> findQrcodeBatchAdmin(QrcodeBatch qrcodeBatch,
    @RequestParam(defaultValue = "1",required = false,value = "pageNum") Integer pageNum
                                                         ) {
        Result<PageInfo<QrcodeBatchVO>> result = new Result<>();
        PageInfo<QrcodeBatch> batch = qrcodeBatchService.selectQrcodeBatch(qrcodeBatch, pageNum);
        PageInfo<QrcodeBatchVO> batchVO = buildQrcodeBatchVO(batch);
        result.code(HttpStatus.OK.value()).data(batchVO).message("success");
        return result;
    }

    //
    @GetMapping("/find")
    @ApiOperation(value = "user的不定项查询")
    public Result<PageInfo<QrcodeBatchVO>> findQrcodeBatch(QrcodeBatch qrcodeBatch,
    @RequestParam(defaultValue = "1",required = false,value = "pageNum") Integer pageNum,
     HttpSession session
    ) {
        //System.out.println(qrcodeBatch+"====");

        Result<PageInfo<QrcodeBatchVO>> result = new Result<>();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            qrcodeBatch.setUserId(user.getUserId());
        }
        PageInfo<QrcodeBatch> batch = qrcodeBatchService.selectQrcodeBatch(qrcodeBatch, pageNum);
        PageInfo<QrcodeBatchVO> voBatch = buildQrcodeBatchVO(batch);
        result.code(HttpStatus.OK.value()).data(voBatch).message("success");
        return result;
    }
    private PageInfo<QrcodeBatchVO> buildQrcodeBatchVO(PageInfo<QrcodeBatch> batch) {
        List<QrcodeBatch> list = batch.getList();
        List<QrcodeBatchVO> voList = new ArrayList<>();
        for (QrcodeBatch qrcodeBatch:list) {
            QrcodeBatchVO vo = new QrcodeBatchVO();
            BeanUtil.copyProperties(qrcodeBatch,vo);
            Integer addressId = qrcodeBatch.getUserAddressId();
            if (addressId != null) {
                vo.setUserAddress(userAddressService.selectByPrimaryKey(addressId));
            }
            vo.setUserName(userService.selectUser(qrcodeBatch.getUserId()).getUserName());
            voList.add(vo);
        }
        PageInfo<QrcodeBatchVO> voPageInfo = new PageInfo<>();
        BeanUtil.copyProperties(batch,voPageInfo,"list");
        voPageInfo.setList(voList);
        return voPageInfo;
    }

    @PostMapping("/addBatch")
    @ApiOperation(value = "这里的number是要多少,so一律是正数。参数是list类型的QrcodeBatch.")
    public Result<Integer> insertQrcodeBatch(@RequestBody @Valid List<QrcodeBatch> batches,
                                             Errors errors,
                                             HttpSession session) {
        //System.out.println(batches);
        Result<Integer> result = new Result<>();
        User user = (User) session.getAttribute("user");
        if (errors.hasErrors()) {
            return Result.generateBadRequestResult(errors);
        }
        if (user == null) {
            result.code(HttpStatus.UNAUTHORIZED.value()).message("未登录");
            return result;
        }
        Integer userId = user.getUserId();
        //检查是否有足够数量的二维码
        HashMap<String,Integer> map = new HashMap<>();
        String typeName = checkIfEnough(userId,batches,map);
        //System.out.println(map);
        if (typeName != null) {
            return result.code(HttpStatus.BAD_REQUEST.value()).message(typeName+"数量不足");
        }
        //插入
        qrcodeBatchService.insertList(batches,userId);
        //修改account
        updateAccount(map,userId);
        result.code(HttpStatus.CREATED.value()).message("success");
        return result;
    }
    private void updateAccount(HashMap<String,Integer> map,Integer userId) {
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String,Integer> entry = iterator.next();
            Account account = new Account();
            account.setUserId(userId);
            account.setGoodsTypeName(entry.getKey());
            account.setGoodsTypeQrcodeQuantity(entry.getValue());
            accountService.updateAccount0(account);
        }
    }


    private String checkIfEnough(Integer userId,List<QrcodeBatch> batches,HashMap<String,Integer> map) {

        for (QrcodeBatch qrcode : batches) {
            String typeName = qrcode.getGoodsTypeName();
            Integer quantity = qrcode.getQrcodeNumber();
            //通过qrcodeId查出type，再查account看对应type是否足够，
            //每个type存到map里面，方便比较
            Integer originQuantity = 0;
            if (map.containsKey(typeName)) {
                originQuantity = map.get(typeName);
            } else {
                Account account = new Account();
                account.setUserId(userId);
                account.setGoodsTypeName(qrcode.getGoodsTypeName());
                List<Account> as = accountService.selectAccountBySelective(account);
                if (as.size() == 0) {
                    return typeName;
                }
                originQuantity = as.get(0).getGoodsTypeQrcodeQuantity();
            }
            //System.out.println("origin = "+originQuantity);
            if (originQuantity - quantity < 0) {
                return typeName;
            } else {
                map.put(typeName,originQuantity - quantity);
            }
        }
        return null;
    }

    @PutMapping("/updateBatch")
    @ApiOperation(value = "batchId必传，number为变化了多少（正负区分）,如果种类错了直接删除重建batch,这里面传goodsTypeName是为了查表，所以必须传。如果已经送出去了，number,type,qrcodeId不能改")
    public Result<Integer> updateQrcodeBatch(@RequestBody QrcodeBatch qrcodeBatch) {
        Result<Integer> result = new Result<>();
        //
        if (qrcodeBatch.getQrcodeBatchId() == null) {
            return result.code(HttpStatus.BAD_REQUEST.value()).message("qrcodeBathId未知");
        }
        if (qrcodeBatch.getGoodsTypeName() == null) {
            return result.code(HttpStatus.BAD_REQUEST.value()).message("种类未知");
        }
        boolean flag = qrcodeBatchService.checkIfMailed(qrcodeBatch);
        if (flag) {
            if (qrcodeBatch.getQrcodeNumber() != null || qrcodeBatch.getQrcodeId() != null) {
                return result.code(HttpStatus.BAD_REQUEST.value()).message("数量,qrcodeId不能修改");
            }
        }
        Integer r = qrcodeBatchService.updateQrcodeBatch(qrcodeBatch);
        if (r < 0) {
            return result.code(HttpStatus.BAD_REQUEST.value()).message("没有足够的二维码");
        }
        return result.code(HttpStatus.OK.value()).message("success");
    }

    @DeleteMapping("/deleteBatch")
    @ApiOperation(value = "直接删除，表里没有isDeleted。userId，goodsTypeName，batchId,ismail,quantity（真实个数，只有正数）必须传,cuz如果ismail为0需要恢复account")
    public Result<Integer> deleteQrcodeBatch(@RequestBody QrcodeBatch qrcodeBatch) {
        Result<Integer> result = new Result<>();
        qrcodeBatchService.deleteQrcodeBatch(qrcodeBatch);
        return result.code(HttpStatus.OK.value()).message("success");
    }


}
