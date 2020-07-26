package com.qrcodemall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderFormDetail implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.order_form_deatil_id
     *
     * @mbg.generated
     */
    private Integer orderFormDeatilId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.order_form_id
     *
     * @mbg.generated
     */
    private Integer orderFormId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.goods_id
     *
     * @mbg.generated
     */
    private Integer goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.goods_name
     *
     * @mbg.generated
     */
    private String goodsName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.goods_price
     *
     * @mbg.generated
     */
    private BigDecimal goodsPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.goods_qrcode_quantity
     *
     * @mbg.generated
     */
    private Integer goodsQrcodeQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.goods_type_name
     *
     * @mbg.generated
     */
    private String goodsTypeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.gmt_created
     *
     * @mbg.generated
     */
    private Date gmtCreated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_form_detail.is_deleted
     *
     * @mbg.generated
     */
    private Integer isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table order_form_detail
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.order_form_deatil_id
     *
     * @return the value of order_form_detail.order_form_deatil_id
     *
     * @mbg.generated
     */
    public Integer getOrderFormDeatilId() {
        return orderFormDeatilId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.order_form_deatil_id
     *
     * @param orderFormDeatilId the value for order_form_detail.order_form_deatil_id
     *
     * @mbg.generated
     */
    public void setOrderFormDeatilId(Integer orderFormDeatilId) {
        this.orderFormDeatilId = orderFormDeatilId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.order_form_id
     *
     * @return the value of order_form_detail.order_form_id
     *
     * @mbg.generated
     */
    public Integer getOrderFormId() {
        return orderFormId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.order_form_id
     *
     * @param orderFormId the value for order_form_detail.order_form_id
     *
     * @mbg.generated
     */
    public void setOrderFormId(Integer orderFormId) {
        this.orderFormId = orderFormId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.goods_id
     *
     * @return the value of order_form_detail.goods_id
     *
     * @mbg.generated
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.goods_id
     *
     * @param goodsId the value for order_form_detail.goods_id
     *
     * @mbg.generated
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.goods_name
     *
     * @return the value of order_form_detail.goods_name
     *
     * @mbg.generated
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.goods_name
     *
     * @param goodsName the value for order_form_detail.goods_name
     *
     * @mbg.generated
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.goods_price
     *
     * @return the value of order_form_detail.goods_price
     *
     * @mbg.generated
     */
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.goods_price
     *
     * @param goodsPrice the value for order_form_detail.goods_price
     *
     * @mbg.generated
     */
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.goods_qrcode_quantity
     *
     * @return the value of order_form_detail.goods_qrcode_quantity
     *
     * @mbg.generated
     */
    public Integer getGoodsQrcodeQuantity() {
        return goodsQrcodeQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.goods_qrcode_quantity
     *
     * @param goodsQrcodeQuantity the value for order_form_detail.goods_qrcode_quantity
     *
     * @mbg.generated
     */
    public void setGoodsQrcodeQuantity(Integer goodsQrcodeQuantity) {
        this.goodsQrcodeQuantity = goodsQrcodeQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.goods_type_name
     *
     * @return the value of order_form_detail.goods_type_name
     *
     * @mbg.generated
     */
    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.goods_type_name
     *
     * @param goodsTypeName the value for order_form_detail.goods_type_name
     *
     * @mbg.generated
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName == null ? null : goodsTypeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.gmt_created
     *
     * @return the value of order_form_detail.gmt_created
     *
     * @mbg.generated
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.gmt_created
     *
     * @param gmtCreated the value for order_form_detail.gmt_created
     *
     * @mbg.generated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.gmt_modified
     *
     * @return the value of order_form_detail.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.gmt_modified
     *
     * @param gmtModified the value for order_form_detail.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_form_detail.is_deleted
     *
     * @return the value of order_form_detail.is_deleted
     *
     * @mbg.generated
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_form_detail.is_deleted
     *
     * @param isDeleted the value for order_form_detail.is_deleted
     *
     * @mbg.generated
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form_detail
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderFormDeatilId=").append(orderFormDeatilId);
        sb.append(", orderFormId=").append(orderFormId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", goodsQrcodeQuantity=").append(goodsQrcodeQuantity);
        sb.append(", goodsTypeName=").append(goodsTypeName);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}