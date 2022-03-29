package com.qrcodemall.controller;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.controller.vo.*;
import com.qrcodemall.entity.*;
import com.qrcodemall.scheduleTask.DynamicTask;
import com.qrcodemall.service.*;
import com.qrcodemall.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
//delete直接在url拼id
/**
 * @Author: Peony
 * @Date: 2020/7/23 15:54
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    //@Autowired
    @Resource
    AdminService adminService;

    @Resource
    UserService userService;

    @Resource
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
    ScheduleTaskService scheduleTaskService;

    @Autowired
    private DynamicTask task;

    @Autowired
    HttpSession session;

    @Autowired
    HttpServletRequest httpServletRequest;

    @PostMapping("/login")//密码登录
    public Result login(@RequestBody @Valid AdminLoginVO admin, Errors errors,
                        HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        if (errors.hasErrors()) {
            return Result.generateBadRequestResult(errors);
        }

        String account = admin.getAccount();
        String password = admin.getPassword();
        System.out.println(account+" *** "+password);
        Integer r = adminService.login(account,password);
        if (r == 1) {
            session.setAttribute("admin","admin");
            session.setMaxInactiveInterval(3600);
            String id = session.getId();
            HttpCookie cookie = CookieUtils.generateSetCookie3(request, "JSESSIONID", id, Duration.ofHours(3));
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            result.setCode(HttpStatus.OK.value());
            result.setMessage("登录成功");
            return result;
        }
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("该用户未注册或者密码错误");
        return result;
    }


    @GetMapping("/logout")//退出登录
    public Result logout(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
        session.removeAttribute("admin");
        String id = session.getId();
        HttpCookie cookie = CookieUtils.generateSetCookie3(request, "JSESSIONID", id, Duration.ZERO);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
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
    public Result<PageInfo<Goods>> selectGoods(GoodsVO goodsVO,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
    @RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime) {
        System.out.println("goodsVo = "+goodsVO);
        Result<PageInfo<Goods>> result = new Result();
        Goods goods = new Goods();
        BeanUtil.copyProperties(goodsVO,goods);
        if (goodsVO.getGoodsTypeName() != null) {
            GoodsType goodsType = goodsTypeService.selectByGoodsTypeName(goodsVO.getGoodsTypeName());
            if (goodsType == null) {
                result.setCode(HttpStatus.BAD_REQUEST.value());
                result.setMessage("no such goods type");
                return result;
            }
            goods.setGoodsTypeId(goodsType.getGoodsTypeId());
        }
        //System.out.println(goods);
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

    public Result insertGoods(/** @Valid @RequestBody Goods goods **/
    Integer goodsTypeId,
    String goodsName,
    BigDecimal goodsPrice,
    String goodsTypeName,
    Integer goodsQrcodeQuantity) {
        Result result = new Result();
        if (goodsTypeId == null || goodsName == null || goodsQrcodeQuantity == null || goodsTypeName == null
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

    @PostMapping("/goodsPromotion")
    public Result createGoodsPromotion(@RequestBody PromotionGoodsVO promotionGoodsvo) {
        System.out.println("createGoodsPromotion==>"+promotionGoodsvo);
        Result result = new Result();
        Date startTime = DateUtil.strToDate(promotionGoodsvo.getPromotionStartTime());
        if (promotionGoodsvo.getPromotionCount() <= 0 ||
                promotionGoodsvo.getPromotionValue().compareTo(new BigDecimal(0)) < 1 ||
        startTime.before(new Date())) {
            result.code(HttpStatus.BAD_REQUEST.value()).message("请校验参数");
            return result;
        }
        PromotionGoods promotionGoods = new PromotionGoods();
        promotionGoods.setGoodsId(promotionGoodsvo.getGoodsId());
        promotionGoods.setPromotionCount(promotionGoodsvo.getPromotionCount());
        promotionGoods.setPromotionValue(promotionGoodsvo.getPromotionValue());
        promotionGoods.setPromotionDuration(promotionGoodsvo.getPromotionDuration());
        promotionGoods.setPromotionStartTime(startTime);
        Integer promotionId = goodsService.createPromotion(promotionGoods);
//        添加该商品的开始和结束的定时任务
        scheduleTaskService.createTask(promotionId,promotionGoodsvo.getGoodsId(),promotionGoodsvo.getPromotionStartTime(),
                promotionGoodsvo.getPromotionDuration());

        return result.code(HttpStatus.OK.value()).message("success").data(promotionId);
    }

    @DeleteMapping("/stopPromotion")
    public Result stopPromotion(Integer goodsId) {
        //上面获取所有goods信息时返回promotionId，这里就很好写，但是上面更改很麻烦，可能出bug
        System.out.println("stopPromotion==>"+goodsId);
        goodsService.cancelPromotion(goodsId);
        scheduleTaskService.deleteTaskByGoodsId(goodsId);
        return Result.generateSuccessResult(goodsId,null);
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
        System.out.println("adduser in");
        Result result = new Result();

        if (errors.hasErrors()) {
//            System.out.println("adduser "+errors);
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //存数据库
        Integer r = userService.addUser(user);
        if (r < 0) {
            return Result.badUserParams(r);
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
        if (r < 0) {
            return Result.badUserParams(r);
        }
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

    @GetMapping("/weeklySales")
    @ApiOperation("昨天是arr[6],前天是arr[5],类推,只查询订单状态为已完成的即status=1")
    public Result<BigDecimal[]> selectWeeklySales() {
        Result<BigDecimal[]> result = new Result<>();
        BigDecimal[] arr = new BigDecimal[7];
        Date curDate = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date origin = null;
        try {
            origin = df.parse(df.format(curDate));
        } catch (ParseException e) {
            GlobalException.fail("后台日期转换出错了");
        }
        System.out.println(origin);
        for (int i = 0; i < 7; i++) {
            Date time = new Date(origin.getTime() - (i) * 24 * 60 * 60 * 1000);
            Date time2 = new Date(origin.getTime() - (i + 1) * 24 * 60 * 60 * 1000);
            System.out.println(time2+" "+time);
            BigDecimal tbd = orderFormService.getSalesSituation(time2,time);
            arr[6 - i] = tbd;
        }
        return result.code(HttpStatus.OK.value()).data(arr).message("success");
    }

}
