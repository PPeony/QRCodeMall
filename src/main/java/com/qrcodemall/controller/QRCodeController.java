package com.qrcodemall.controller;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.controller.vo.QrcodeVO;
import com.qrcodemall.entity.*;
import com.qrcodemall.service.*;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/8/14 14:08
 */
@RestController
@RequestMapping("/qrcode")
@Api(tags = {"二维码接口，扫描，制作"})
public class QRCodeController {

    @Autowired
    PropertyService propertyService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    QrcodeService qrcodeService;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    HttpSession session;

/**property**/
    @ApiOperation(value = "根据种类查出字段", notes = "参数为goodsType")
    @GetMapping("/goodsType")
    public Result<List<Property>> selectByGoodsType(String goodsType) {
        Result<List<Property>> result = new Result<>();
        if (goodsType.equals("")) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请输入参数");
            return result;
        }
        //service根据type查询
        List<Property> properties = propertyService.selectPropertyByType(goodsType);
        result.setData(properties);
        result.setCode(HttpStatus.OK.value());
        return result;
    }

    @ApiOperation(value = "添加字段",notes = "一次请求只能加一个字段,字段不能重复")

    /*
    example:
    [
  {
    "gmtCreated": "2020-08-14T07:40:52.263Z",
    "gmtModified": "2020-08-14T07:40:52.263Z",
    "goodsTypeId": 0,
    "goodsTypeName": "string",
    "isDeleted": 0,
    "propertyId": 0,
    "propertyKey": "string"
  },
  {
    "gmtCreated": "2020-08-14T07:40:52.263Z",
    "gmtModified": "2020-08-14T07:40:52.263Z",
    "goodsTypeId": 0,
    "goodsTypeName": "string",
    "isDeleted": 0,
    "propertyId": 0,
    "propertyKey": "string"
  }
]
     */
    @PostMapping("/addKey")
    public Result insertKey(@Valid @RequestBody List<Property> properties, Errors errors) {
        System.out.println("addKey");
        System.out.println(properties);
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        if (!BeanUtil.checkIfRepeat(properties,"propertyKey")) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请求参数含有重复值");
            return result;
        }
        //add
        Integer r = propertyService.insertProperty(properties);
        if (r <= 0) {
            r *= -1;
            result.setMessage("第"+r+"个参数重复");
            result.setCode(HttpStatus.BAD_REQUEST.value());
            return result;
        }
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @ApiOperation(value = "修改",notes = "propertyKey不能为空")
    @PutMapping("/updateKey")
    public Result updateKey(@RequestBody List<Property> properties) {
        Result result = new Result();
        if (!BeanUtil.checkIfRepeat(properties,"propertyKey")) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请求参数含有重复值");
            return result;
        }
        //update
        Integer r = propertyService.updateProperty(properties);
        if (r <= 0) {
            r *= -1;
            result.setMessage("第"+r+"个参数重复");
            result.setCode(HttpStatus.BAD_REQUEST.value());
            return result;
        }
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @DeleteMapping("/deleteKey")
    public Result deleteKey(Integer propertyId) {
        Result result = new Result();
        //delete
        propertyService.deleteProperty(propertyId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    /**property_value**/
    @ApiOperation(value = "通过qrcodeid查找对应信息",notes = "注意返回为list，需要前端再拆分")
    @GetMapping("/{QRCodeId}")
    public Result<List<PropertyValue>> selectQrCodeId(@PathVariable Integer QRCodeId) {
        Result<List<PropertyValue>> result = new Result<>();
        //select
        List<PropertyValue> list = propertyValueService.selectByQRCodeId(QRCodeId);
        result.setData(list);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @PostMapping("/addValue")
    public Result addValue(@Valid @RequestBody List<PropertyValue> propertyValues,Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //add
        Integer r = propertyValueService.insertPropertyValue(propertyValues);
        if (r <= 0) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("此二维码已存在，无需重复添加");
            return result;
        }
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @ApiOperation(value = "更新数据",notes = "注意更新依靠qrcodeId")
    @PutMapping("/updateValue")
    public Result updateValue(@RequestBody List<PropertyValue> propertyValues) {
        Result result = new Result();
        //update
        Integer r = propertyValueService.updatePropertyValue(propertyValues);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @DeleteMapping("/deleteValue")
    public Result deleteValue(Integer propertyValueId) {
        Result result = new Result();
        //delete
        propertyValueService.deletePropertyValue(propertyValueId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }
    /**qrcode**/
    @GetMapping("/myQrcode")
    @ApiOperation(value = "参数传一个pageNum")
    public Result<PageInfo<Qrcode>> selectMyQrcode(HttpServletRequest request) {
        Result<PageInfo<Qrcode>> result = new Result<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return result.code(HttpStatus.UNAUTHORIZED.value()).message("请登录");
        }
        User user2 = userService.selectUser(user.getUserId());
        PageInfo<Qrcode> list = qrcodeService.selectQrcode(user2.getUserName(),Integer.valueOf(request.getParameter("pageNum")));
        return result.code(HttpStatus.OK.value()).data(list).message("success");
    }

    @PostMapping("/addQrcode")
    @ApiOperation(value = "返回值为新增的qrcodeid")
    public Result<Integer> addQrcode(@RequestBody @Valid Qrcode qrcode,Errors errors) {
        Result<Integer> result = new Result<>();
        if (errors.hasErrors()) {
            return Result.generateBadRequestResult(errors);
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.code(HttpStatus.UNAUTHORIZED.value())
                    .message("请登录");
            return result;
        }
        qrcode.setUserId(user.getUserId());
        int r = qrcodeService.insertQrcode(qrcode);
        return result.code(HttpStatus.OK.value()).data(r).message("success");
    }

    @GetMapping("/byName")
    public Result<PageInfo<QrcodeVO>> selectQRCode(String userName,
                                                   @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        Result<PageInfo<QrcodeVO>> result = new Result<>();
        if (userName == null || userName.length() == 0) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请输入用户名");
            return result;
        }

        PageInfo<Qrcode> list = qrcodeService.selectQrcode(userName, pageNum);
        //PageInfo<QrcodeVO> ql = new PageInfo<>(buildQrcodeVO(list.getList()));
        //上下两种写法不一样，下面应该合理
        PageInfo<QrcodeVO> ql = new PageInfo<>();
        BeanUtil.copyProperties(list,ql,"list");
        ql.setList(buildQrcodeVO(list.getList()));
        result.setCode(HttpStatus.OK.value());
        result.setData(ql);
        result.setMessage("成功");
        return result;
    }

    private List<QrcodeVO> buildQrcodeVO(List<Qrcode> list) {
        List<QrcodeVO> res = new LinkedList<>();
        for (Qrcode qrcode : list) {
            QrcodeVO qv = new QrcodeVO();
            BeanUtil.copyProperties(qrcode,qv);
            User su = userService.selectUser(qrcode.getUserId());
            Goods sg = goodsService.selectGoods(qrcode.getGoodsId());
            qv.setGoodsName(sg.getGoodsName());
            qv.setUserName(su.getUserName());
            res.add(qv);
        }
        return res;
    }

    @PutMapping("/updateQRCode")
    public Result<Integer> updateQrcode(@RequestBody Qrcode qrcode) {
        Result<Integer> result = new Result();
        ///
        qrcodeService.updateQrcode(qrcode);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("update success");
        return result;
    }

    @DeleteMapping("/deleteQRCode")
    public Result deleteQrcode(Integer qrcodeId) {
        Result result = new Result();
        ///
        qrcodeService.deleteQrcode(qrcodeId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }


}
