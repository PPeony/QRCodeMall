package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.Qrcode;

/**
 * @Author: Peony
 * @Date: 2020/7/27 11:44
 */
public interface QrcodeService {

    PageInfo<Qrcode> selectQrcode(String userName, Integer pageNum);

    Integer insertQrcode(Qrcode qrcode);

    Integer updateQrcode(Qrcode qrcode);

    Integer deleteQrcode(Integer qrcodeId);
}
