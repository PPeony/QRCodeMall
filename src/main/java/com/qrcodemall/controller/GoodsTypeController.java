package com.qrcodemall.controller;

import com.qrcodemall.entity.GoodsType;
import com.qrcodemall.service.GoodsTypeService;
import com.qrcodemall.util.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/28 13:49
 */
@RestController
@RequestMapping("/goodsType")
public class GoodsTypeController {
    @Autowired
    GoodsTypeService goodsTypeService;

    @GetMapping("/all")
    public Result selectAllGoodsType() {
        Result<List<GoodsType>> result = new Result<>();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(goodsTypeService.selectAllGoodsType());
        return result;
    }

    @PostMapping("/add")
    public Result insertGoodsType(@Valid @RequestBody GoodsType goodsType, Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        Integer r = goodsTypeService.insertGoodsType(goodsType);
        if (r < 0) {
            result.setCode(HttpStatus.OK.value());
            result.setMessage("名字重复");
            return result;
        }
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("add success");
        return result;
    }

    @PutMapping("/update")
    public Result updateGoodsType(@Valid @RequestBody GoodsType goodsType, Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        Integer r = goodsTypeService.updateGoodsType(goodsType);
        if (r < 0) {
            result.setCode(HttpStatus.OK.value());
            result.setMessage("名字重复");
            return result;
        }
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("update success");
        return result;
    }

    @DeleteMapping("/delete")
    public Result deleteGoodsType(Integer goodsTypeId) {
        Result result = new Result();
        goodsTypeService.deleteGoodsType(goodsTypeId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
}
