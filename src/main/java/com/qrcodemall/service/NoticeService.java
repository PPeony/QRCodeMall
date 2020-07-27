package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.Notice;

import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2020/7/27 11:45
 */
public interface NoticeService {

    PageInfo<Notice> selectNotice(Notice notice, Integer pageNum, Date beginTime, Date endTime);

    Integer insertNotice(Notice notice);

    Integer updateNotice(Notice notice);

    Integer deleteNotice(Integer noticeId);
}
