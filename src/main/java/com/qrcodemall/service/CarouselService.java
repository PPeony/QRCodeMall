package com.qrcodemall.service;

import com.qrcodemall.entity.Carousel;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/2 14:50
 */
public interface CarouselService {

    List<Carousel> selectAllCarousel();

    Carousel selectOneCarousel(Integer carouselId);

    Integer addCarousel(Carousel carousel);

    Integer updateCarousel(Carousel carousel);

    Integer deleteCarousel(Integer carouselId);
}
