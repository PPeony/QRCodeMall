package com.qrcodemall.entity;

import java.io.Serializable;

public class Account implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.account_id
     *
     * @mbg.generated
     */
    private Integer accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.goods_type_name
     *
     * @mbg.generated
     */
    private String goodsTypeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.goods_type_qrcode_quantity
     *
     * @mbg.generated
     */
    private Integer goodsTypeQrcodeQuantity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table account
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.account_id
     *
     * @return the value of account.account_id
     *
     * @mbg.generated
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.account_id
     *
     * @param accountId the value for account.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.user_id
     *
     * @return the value of account.user_id
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.user_id
     *
     * @param userId the value for account.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.goods_type_name
     *
     * @return the value of account.goods_type_name
     *
     * @mbg.generated
     */
    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.goods_type_name
     *
     * @param goodsTypeName the value for account.goods_type_name
     *
     * @mbg.generated
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName == null ? null : goodsTypeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.goods_type_qrcode_quantity
     *
     * @return the value of account.goods_type_qrcode_quantity
     *
     * @mbg.generated
     */
    public Integer getGoodsTypeQrcodeQuantity() {
        return goodsTypeQrcodeQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.goods_type_qrcode_quantity
     *
     * @param goodsTypeQrcodeQuantity the value for account.goods_type_qrcode_quantity
     *
     * @mbg.generated
     */
    public void setGoodsTypeQrcodeQuantity(Integer goodsTypeQrcodeQuantity) {
        this.goodsTypeQrcodeQuantity = goodsTypeQrcodeQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountId=").append(accountId);
        sb.append(", userId=").append(userId);
        sb.append(", goodsTypeName=").append(goodsTypeName);
        sb.append(", goodsTypeQrcodeQuantity=").append(goodsTypeQrcodeQuantity);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}