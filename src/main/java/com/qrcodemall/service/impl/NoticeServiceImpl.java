package com.qrcodemall.service.impl;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.dao.NoticeMapper;
import com.qrcodemall.entity.Notice;
import com.qrcodemall.service.NoticeService;
import com.qrcodemall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2022/3/7 12:00
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;


    @Override
    public PageInfo<Notice> selectNotice(Notice notice, Integer pageNum, Date beginTime, Date endTime) {
        return PageUtil.generatePageInfoByTime(notice, pageNum, beginTime, endTime, noticeMapper);
    }


    @Override
    public Integer insertNotice(Notice notice) {
        notice.setGmtCreated(new Date());
        notice.setGmtModified(new Date());
        return noticeMapper.insertSelective(notice);

    }

    @Override
    public Integer updateNotice(Notice notice) {
        notice.setGmtModified(new Date());
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public Integer deleteNotice(Integer noticeId) {
        Notice notice = new Notice();
        notice.setNoticeId(noticeId);
        notice.setIsDeleted(1);
        notice.setGmtModified(new Date());
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }
}
