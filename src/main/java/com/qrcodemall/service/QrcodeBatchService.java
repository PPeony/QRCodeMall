package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.QrcodeBatch;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/6 13:37
 */
public interface QrcodeBatchService {
    PageInfo<QrcodeBatch> selectQrcodeBatch(QrcodeBatch qrcodeBatch,int pageNum);

    Integer insertList(List<QrcodeBatch> qrcodeBatches,Integer userId);

    Integer updateQrcodeBatch(QrcodeBatch qrcodeBatch);

    Integer insertQrcodeBatch(QrcodeBatch qrcodeBatch);

    Integer deleteQrcodeBatch(QrcodeBatch qrcodeBatch);

    boolean checkIfMailed(QrcodeBatch qrcodeBatch);
}
