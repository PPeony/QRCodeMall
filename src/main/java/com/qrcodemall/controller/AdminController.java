package com.qrcodemall.controller;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.controller.vo.AdminLoginVO;
import com.qrcodemall.controller.vo.GoodsVO;
import com.qrcodemall.controller.vo.NoticeVO;
import com.qrcodemall.controller.vo.QrcodeVO;
import com.qrcodemall.entity.*;
import com.qrcodemall.service.*;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.PictureUtil;
import com.qrcodemall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
//delete直接在url拼id
/**
 * @Author: Peony
 * @Date: 2020/7/23 15:54
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserBillService userBillService;

    @Autowired
    QrcodeService qrcodeService;

    @Autowired
    UserAddressService userAddressService;

    @Autowired
    GoodsTypeService goodsTypeService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    OrderFormService orderFormService;


    @Autowired
    HttpSession session;

    @Autowired
    HttpServletRequest httpServletRequest;

    @PostMapping("/login")//密码登录
    public Result login(@RequestBody @Valid AdminLoginVO admin,Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            return Result.generateBadRequestResult(errors);
        }

        String account = admin.getAccount();
        String password = admin.getPassword();
        System.out.println(account+" *** "+password);


        Integer r = adminService.login(account,password);
        if (r == 1) {
            result.setCode(HttpStatus.OK.value());
            result.setMessage("登录成功");
            return result;
        }
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("该用户未注册");
        return result;
    }


    @GetMapping("/logout")//退出登录
    public Result logout(HttpSession session) {
        session.removeAttribute("adminId");
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

//    @PostMapping("/signin")//手机验证码登录,admin不需要
//    public Result signin(String phone, HttpSession session) {
//        Result result = new Result();
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("登录成功");
//        return result;
//    }
/**goods**/
    @GetMapping("/goods")
    public Result<PageInfo<Goods>> selectGoods(Goods goods,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
    @RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime) {
        Result<PageInfo<Goods>> result = new Result();
        System.out.println(goods);
        //全空查询所有
        //分页查询,一页十个，返回
        /*
        List<Goods> list = new LinkedList<>();
        Goods findGoods = new Goods();
        findGoods.setGoodsId(1);
        findGoods.setGoodsName("testGoodsA");
        list.add(findGoods);
         */
        PageInfo<Goods> list = goodsService.selectGoods(goods, pageNum, beginTime, endTime);
        result.setCode(HttpStatus.OK.value());
        result.setData(list);
        result.setMessage("成功");
        return result;
    }

    @PostMapping("/addGoods")//添加商品
    //todo,传图片
    public Result insertGoods(/** @Valid @RequestBody Goods goods **/
    Integer goodsTypeId,
    String goodsName,
    BigDecimal goodsPrice,
    String goodsTypeName,
    Integer goodsQrcodeQuantity,
    MultipartFile goodsPicture,
    MultipartFile goodsDetail,
    String goodsIntroduction,
    Integer goodsStatus) {
        Result result = new Result();
        if (goodsTypeId == null || goodsName == null || goodsPicture == null ||
        goodsPicture == null || goodsDetail == null || goodsIntroduction == null
                || goodsQrcodeQuantity == null || goodsTypeName == null
        ) {
            result.setMessage("缺少参数");
            result.setCode(HttpStatus.BAD_REQUEST.value());
            return result;
        }
        Goods goods = new Goods();
        //System.out.println(goods);
        // decimal的json直接写12.6就行，不用引号
        goods.setGoodsName(goodsName);
        goods.setGoodsTypeId(goodsTypeId);
        goods.setGoodsTypeName(goodsTypeName);
        goods.setGoodsPrice(goodsPrice);
        goods.setGoodsQrcodeQuantity(goodsQrcodeQuantity);
        String goodsDetailName = PictureUtil.uploadFile(goodsDetail,httpServletRequest);
        goods.setGoodsDetail(goodsDetailName);
        goods.setGoodsStatus(goodsStatus);
        String goodsPictureName = PictureUtil.uploadFile(goodsPicture,httpServletRequest);
        goods.setGoodsPicture(goodsPictureName);
        goods.setGoodsIntroduction(goodsIntroduction);
        System.out.println(goods);
        //insert

        Integer r = goodsService.insertGoods(goods);
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @PutMapping("/updateGoods")//所有的update都是根据id来的
    public Result updateGoods(/** @RequestBody Goods goods **/
      Integer goodsTypeId,
      String goodsName,
      BigDecimal goodsPrice,
      String goodsTypeName,
      Integer goodsQrcodeQuantity,
      MultipartFile goodsPicture,
      MultipartFile goodsDetail,
      String goodsIntroduction,
      Integer goodsStatus

    ) {
        Goods goods = new Goods();
        Result result = new Result();
        if (goodsPicture != null) {
            String name = PictureUtil.uploadFile(goodsPicture,httpServletRequest);
            goods.setGoodsPicture(name);
        }
        if (goodsDetail != null) {
            String name = PictureUtil.uploadFile(goodsDetail,httpServletRequest);
            goods.setGoodsDetail(name);
        }
        goods.setGoodsTypeId(goodsTypeId);
        goods.setGoodsName(goodsName);
        goods.setGoodsPrice(goodsPrice);
        goods.setGoodsTypeName(goodsTypeName);
        goods.setGoodsQrcodeQuantity(goodsQrcodeQuantity);
        goods.setGoodsStatus(goodsStatus);
        goods.setGoodsIntroduction(goodsIntroduction);
        goodsService.updateGoods(goods);
        //update
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @DeleteMapping("/deleteGoods")
    public Result deleteGoods(Integer goodsId) {
        //delete
        Result result = new Result();
        goodsService.deleteGoods(goodsId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
/**notice**/
    @GetMapping("/notice")
    public Result<PageInfo<Notice>> selectNotice(Notice notice,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
    @RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime) {
        Result<PageInfo<Notice>> result = new Result<>();
        PageInfo<Notice> list = noticeService.selectNotice(notice, pageNum, beginTime, endTime);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        result.setData(list);
        return result;
    }

    @PostMapping("/addNotice")
    public Result insertNotice(@Valid @RequestBody Notice notice, Errors errors) {

        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //insert
        noticeService.insertNotice(notice);
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @PutMapping("/updateNotice")//所有的update都是根据id来的
    public Result updateNotice(@RequestBody Notice notice) {
        Result result = new Result();
        //update
        noticeService.updateNotice(notice);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @DeleteMapping("/deleteNotice")
    public Result deleteNotice(Integer noticeId) {
        //delete
        Result result = new Result();
        noticeService.deleteNotice(noticeId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
/**orderForm**/
//下面这些都在orderFormController
//    @GetMapping("/orderForm")
//    public Result selectOrderForm(
//    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
//    @RequestParam(required = false,value = "beginTime") Date beginTime,
//    @RequestParam(required = false,value = "endTime") Date endTime) {
//        Result<List<OrderForm>> result = new Result<>();
//        List<OrderForm> list = new LinkedList<>();
//        //查询分页
//        OrderForm findOrderForm = new OrderForm();
//        findOrderForm.setOrderFormId(111);
//        findOrderForm.setOrderFormPayTime(new Date());
//
//        list.add(findOrderForm);
//        result.setData(list);
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("成功");
//        return result;
//    }

//    //订单详情
//    @GetMapping("/orderForm/{orderFormId}")
//    public Result selectOrderDetails(@PathVariable Integer orderFormId,
//                                     @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
//        //orderDetail表照着id查
//        Result<List<OrderFormDetail>> result = new Result<>();
//        List<OrderFormDetail> list = new LinkedList<>();
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("success");
//        result.setData(list);
//        return result;
//    }
//
//    @PostMapping("/addOrderForm")
//    public Result insertOrderForm(@RequestBody @Valid OrderForm orderForm,Errors errors) {
//        Result result = new Result();
//        if (errors.hasErrors()) {
//            result.setCode(HttpStatus.BAD_REQUEST.value());
//            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
//            return result;
//        }
//        //存数据库
//        result.setCode(HttpStatus.CREATED.value());
//        result.setMessage("add success");
//        return result;
//
//    }
//
//    @PutMapping("/updateOrderForm")
//    public Result updateOrderForm(@RequestBody OrderForm orderForm) {
//        Result result = new Result();
//        ///
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("update success");
//        return result;
//    }
//
//    @DeleteMapping("/deleteOrderForm")
//    public Result deleteOrderForm(Integer orderFormId) {
//        Result result = new Result();
//        ///
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("delete success");
//        return result;
//    }

/**qrcode**/
    @GetMapping("/QRCode")
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

    @PostMapping("/addQRCode")
    public Result insertQrcode(@RequestBody @Valid Qrcode qrcode,Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //存数据库
        qrcodeService.insertQrcode(qrcode);
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("add success");
        return result;
    }

    @PutMapping("/updateQRCode")
    public Result updateQrcode(@RequestBody Qrcode qrcode) {
        Result result = new Result();
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
/**user**/
    @GetMapping("/user")
    public Result<PageInfo<User>> selectUser(User user,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        Result<PageInfo<User>> result = new Result();
        PageInfo<User> list = userService.selectUser(user,pageNum);
        result.setCode(HttpStatus.OK.value());
        result.setData(list);
        result.setMessage("成功");
        return result;
    }
    @PostMapping("/addUser")
    public Result insertUser(@RequestBody @Valid User user,Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //存数据库
        Integer r = userService.addUser(user);
        if (r < 0) {
            result.setCode(HttpStatus.OK.value());
            switch (r) {
                case -1:result.setMessage("没有父级代理名字");break;
                case -2:result.setMessage("名字重复");break;
                case -3:result.setMessage("手机号重复");break;
                case -4:result.setMessage("邮箱重复");break;
            }
            return result;
        }
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("add success");
        return result;
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        Result result = new Result();
        Integer r = userService.updateUser(user);
        ///
        result.setCode(HttpStatus.OK.value());
        result.setMessage("update success");
        return result;
    }

    @DeleteMapping("/deleteUser")
    public Result deleteUser(Integer userId) {
        Result result = new Result();
        ///
        userService.deleteUser(userId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
   /**userBill**/

    @GetMapping("/userBill")
    public Result<PageInfo<UserBill>> selectUserBill(@RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {

        Result<PageInfo<UserBill>> result = new Result();
        PageInfo<UserBill> list = userBillService.selectUserBill(beginTime, endTime, pageNum);

        //一定要用string构造
        result.setCode(HttpStatus.OK.value());
        result.setData(list);
        result.setMessage("成功");
        return result;
    }



    @PutMapping("/outputMoney")//给用户打钱,money是打多少
    //没懂啥功能，放着,todo
    public Result outputMoney(@RequestBody UserBill orign) {
        System.out.println(orign);
        Result result = new Result();
        Integer userId = orign.getUserId();
        BigDecimal money = orign.getUserBillMoney();
        if (userId == null || money == null || money.equals(new BigDecimal("0"))) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请输入正确的参数");
            return result;
        }
        UserBill userBill = new UserBill();
        userBill.setUserId(userId);
        userBill.setUserBillMoney(new BigDecimal("0"));
        BigDecimal userMoney = userBill.getUserBillMoney();
        userMoney = userMoney.add(money);
        userBill.setUserBillMoney(userMoney);
        System.out.println(userBill);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        return result;
    }

/**销售情况总览**/
    @GetMapping("/sales")
    public Result<BigDecimal> selectSales(
            @RequestParam(required = false,value = "beginTime") Date beginTime,
            @RequestParam(required = false,value = "endTime") Date endTime
    ) {

        Result<BigDecimal> result = new Result();
        //从orderForm里面查询符合日期的，在把和相加
        BigDecimal decimal = orderFormService.getSalesSituation(beginTime,endTime);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(decimal);
        return result;
    }

}
