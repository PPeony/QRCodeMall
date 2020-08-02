package com.qrcodemall.controller;

import com.qrcodemall.entity.Carousel;
import com.qrcodemall.service.CarouselService;
import com.qrcodemall.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/8/2 14:49
 */
@RestController
@RequestMapping("/Carousel")
@Api(value = "轮播图")
public class CarouselController {
    @Autowired
    CarouselService carouselService;

    @GetMapping("/all")
    public Result<List<Carousel>> selectAllCarousel() {
        Result<List<Carousel>> result = new Result<>();
        List<Carousel> carousels = carouselService.selectAllCarousel();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(carousels);
        return result;
    }

    @GetMapping("/{carouselId}")
    public Result<Carousel> selectOneCarousel(@PathVariable Integer carouselId) {
        Result<Carousel> result = new Result<>();
        Carousel carousel = carouselService.selectOneCarousel(carouselId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(carousel);
        return result;
    }

    @PostMapping("/addCarousel")
    public Result insertCarousel(@RequestBody Carousel carousel) {
        Result result = new Result();
        carouselService.addCarousel(carousel);
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @PutMapping("/updateCarousel")
    public Result updateCarousel(@RequestBody Carousel carousel) {
        Result result = new Result();
        carouselService.updateCarousel(carousel);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @DeleteMapping("/deleteCarousel")
    public Result deleteCarousel(Integer carouselId) {
        Result result = new Result();
        carouselService.deleteCarousel(carouselId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;

    }
}
