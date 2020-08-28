package com.qrcodemall.controller;

import com.qrcodemall.entity.Carousel;
import com.qrcodemall.service.CarouselService;
import com.qrcodemall.util.PictureUtil;
import com.qrcodemall.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/8/2 14:49
 */
@RestController
@RequestMapping("/Carousel")
@Api(tags = "轮播图")
public class CarouselController {
    @Autowired
    CarouselService carouselService;

    @GetMapping("/all")
    @ApiOperation(value = "no param")
    public Result<List<Carousel>> selectAllCarousel() {
        Result<List<Carousel>> result = new Result<>();
        List<Carousel> carousels = carouselService.selectAllCarousel();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(carousels);
        return result;
    }

    @GetMapping("/{carouselId}")
    @ApiOperation(value = "唯一参数carouselId")
    public Result<Carousel> selectOneCarousel(@PathVariable Integer carouselId) {
        Result<Carousel> result = new Result<>();
        Carousel carousel = carouselService.selectOneCarousel(carouselId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(carousel);
        return result;
    }

    @PostMapping("/addCarousel")
    public Result insertCarousel(MultipartFile carouselImg,
                                 String carouselLink,
                                 HttpServletRequest request) {
        Result result = new Result();
        String name = PictureUtil.uploadFile(carouselImg,request);
        Carousel carousel = new Carousel();
        carousel.setCarouselImgName(name);
        carousel.setCarouselLink(carouselLink);
        carouselService.addCarousel(carousel);
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @PutMapping("/updateCarousel")
    public Result updateCarousel(Integer carouselId,
                                 MultipartFile carouselImg,
                                 String carouselLink,
                                 HttpServletRequest request
                                 ) {
        Result result = new Result();
        Carousel carousel = new Carousel();
        if (carouselImg != null) {
            String name = PictureUtil.uploadFile(carouselImg,request);
            carousel.setCarouselImgName(name);
        }
        carousel.setCarouselLink(carouselLink);
        carousel.setCarouselId(carouselId);
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
