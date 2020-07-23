package com.qrcodemall.entity;

import java.io.Serializable;

public class User implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_name
     *
     * @mbg.generated
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_phone
     *
     * @mbg.generated
     */
    private String userPhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_password
     *
     * @mbg.generated
     */
    private String userPassword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_email
     *
     * @mbg.generated
     */
    private String userEmail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.is_vip
     *
     * @mbg.generated
     */
    private Integer isVip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_point
     *
     * @mbg.generated
     */
    private Integer userPoint;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.is_deleted
     *
     * @mbg.generated
     */
    private Integer isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_father_proxy_id
     *
     * @mbg.generated
     */
    private Integer userFatherProxyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_father_proxy_name
     *
     * @mbg.generated
     */
    private String userFatherProxyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_grandfather_proxy_id
     *
     * @mbg.generated
     */
    private Integer userGrandfatherProxyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_grandfather_proxy_name
     *
     * @mbg.generated
     */
    private String userGrandfatherProxyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_id
     *
     * @return the value of user.user_id
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_id
     *
     * @param userId the value for user.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_name
     *
     * @return the value of user.user_name
     *
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_name
     *
     * @param userName the value for user.user_name
     *
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_phone
     *
     * @return the value of user.user_phone
     *
     * @mbg.generated
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_phone
     *
     * @param userPhone the value for user.user_phone
     *
     * @mbg.generated
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_password
     *
     * @return the value of user.user_password
     *
     * @mbg.generated
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_password
     *
     * @param userPassword the value for user.user_password
     *
     * @mbg.generated
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_email
     *
     * @return the value of user.user_email
     *
     * @mbg.generated
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_email
     *
     * @param userEmail the value for user.user_email
     *
     * @mbg.generated
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.is_vip
     *
     * @return the value of user.is_vip
     *
     * @mbg.generated
     */
    public Integer getIsVip() {
        return isVip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.is_vip
     *
     * @param isVip the value for user.is_vip
     *
     * @mbg.generated
     */
    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_point
     *
     * @return the value of user.user_point
     *
     * @mbg.generated
     */
    public Integer getUserPoint() {
        return userPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_point
     *
     * @param userPoint the value for user.user_point
     *
     * @mbg.generated
     */
    public void setUserPoint(Integer userPoint) {
        this.userPoint = userPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.is_deleted
     *
     * @return the value of user.is_deleted
     *
     * @mbg.generated
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.is_deleted
     *
     * @param isDeleted the value for user.is_deleted
     *
     * @mbg.generated
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_father_proxy_id
     *
     * @return the value of user.user_father_proxy_id
     *
     * @mbg.generated
     */
    public Integer getUserFatherProxyId() {
        return userFatherProxyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_father_proxy_id
     *
     * @param userFatherProxyId the value for user.user_father_proxy_id
     *
     * @mbg.generated
     */
    public void setUserFatherProxyId(Integer userFatherProxyId) {
        this.userFatherProxyId = userFatherProxyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_father_proxy_name
     *
     * @return the value of user.user_father_proxy_name
     *
     * @mbg.generated
     */
    public String getUserFatherProxyName() {
        return userFatherProxyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_father_proxy_name
     *
     * @param userFatherProxyName the value for user.user_father_proxy_name
     *
     * @mbg.generated
     */
    public void setUserFatherProxyName(String userFatherProxyName) {
        this.userFatherProxyName = userFatherProxyName == null ? null : userFatherProxyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_grandfather_proxy_id
     *
     * @return the value of user.user_grandfather_proxy_id
     *
     * @mbg.generated
     */
    public Integer getUserGrandfatherProxyId() {
        return userGrandfatherProxyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_grandfather_proxy_id
     *
     * @param userGrandfatherProxyId the value for user.user_grandfather_proxy_id
     *
     * @mbg.generated
     */
    public void setUserGrandfatherProxyId(Integer userGrandfatherProxyId) {
        this.userGrandfatherProxyId = userGrandfatherProxyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_grandfather_proxy_name
     *
     * @return the value of user.user_grandfather_proxy_name
     *
     * @mbg.generated
     */
    public String getUserGrandfatherProxyName() {
        return userGrandfatherProxyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_grandfather_proxy_name
     *
     * @param userGrandfatherProxyName the value for user.user_grandfather_proxy_name
     *
     * @mbg.generated
     */
    public void setUserGrandfatherProxyName(String userGrandfatherProxyName) {
        this.userGrandfatherProxyName = userGrandfatherProxyName == null ? null : userGrandfatherProxyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", userPhone=").append(userPhone);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userEmail=").append(userEmail);
        sb.append(", isVip=").append(isVip);
        sb.append(", userPoint=").append(userPoint);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", userFatherProxyId=").append(userFatherProxyId);
        sb.append(", userFatherProxyName=").append(userFatherProxyName);
        sb.append(", userGrandfatherProxyId=").append(userGrandfatherProxyId);
        sb.append(", userGrandfatherProxyName=").append(userGrandfatherProxyName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}