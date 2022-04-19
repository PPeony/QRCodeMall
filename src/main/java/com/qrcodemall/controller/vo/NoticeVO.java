package com.qrcodemall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2022/3/24 10:00
 */
@Data
public class NoticeVO implements Serializable {
    private Integer noticeId;
    private String noticeTittleName;
    private String noticeMessage;
    private Date beginTime;
    private Date endTime;
}
