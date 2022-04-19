package com.qrcodemall.service.impl;

import com.qrcodemall.dao.CarouselMapper;
import com.qrcodemall.entity.Carousel;
import com.qrcodemall.entity.CarouselExample;
import com.qrcodemall.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/2 14:53
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    CarouselMapper carouselMapper;

    @Override
    public List<Carousel> selectAllCarousel() {
        CarouselExample example = new CarouselExample();
        CarouselExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        List<Carousel> carousels = carouselMapper.selectByExample(example);
        return carousels;
    }

    @Override
    public Carousel selectOneCarousel(Integer carouselId) {
        CarouselExample example = new CarouselExample();
        CarouselExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andCarouselIdEqualTo(carouselId);
        List<Carousel> carousels = carouselMapper.selectByExample(example);
        if (carousels.size() == 0) {
            return null;
        }
        return carousels.get(0);
    }

    @Override
    public Integer addCarousel(Carousel carousel) {
        carousel.setGmtCreated(new Date());
        carousel.setGmtModified(new Date());
        return carouselMapper.insert(carousel);
    }

    @Override
    public Integer updateCarousel(Carousel carousel) {
        carousel.setGmtModified(new Date());
        return carouselMapper.updateByPrimaryKey(carousel);
    }

    @Override
    public Integer deleteCarousel(Integer carouselId) {
        Carousel carousel = new Carousel();
        carousel.setCarouselId(carouselId);
        carousel.setGmtModified(new Date());
        carousel.setIsDeleted(1);
        return carouselMapper.updateByPrimaryKey(carousel);
    }
}
