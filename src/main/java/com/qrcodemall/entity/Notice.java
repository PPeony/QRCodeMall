package com.qrcodemall.entity;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.notice_id
     *
     * @mbg.generated
     */
    private Integer noticeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.notice_tittle_name
     *
     * @mbg.generated
     */
    @NotEmpty(message = "tittle不能为空")
    private String noticeTittleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.gmt_created
     *
     * @mbg.generated
     */
    private Date gmtCreated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.is_deleted
     *
     * @mbg.generated
     */
    private Integer isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.notice_message
     *
     * @mbg.generated
     */
    @NotEmpty(message = "主体信息不能为空")
    private String noticeMessage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table notice
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.notice_id
     *
     * @return the value of notice.notice_id
     *
     * @mbg.generated
     */
    public Integer getNoticeId() {
        return noticeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.notice_id
     *
     * @param noticeId the value for notice.notice_id
     *
     * @mbg.generated
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.notice_tittle_name
     *
     * @return the value of notice.notice_tittle_name
     *
     * @mbg.generated
     */
    public String getNoticeTittleName() {
        return noticeTittleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.notice_tittle_name
     *
     * @param noticeTittleName the value for notice.notice_tittle_name
     *
     * @mbg.generated
     */
    public void setNoticeTittleName(String noticeTittleName) {
        this.noticeTittleName = noticeTittleName == null ? null : noticeTittleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.gmt_created
     *
     * @return the value of notice.gmt_created
     *
     * @mbg.generated
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.gmt_created
     *
     * @param gmtCreated the value for notice.gmt_created
     *
     * @mbg.generated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.gmt_modified
     *
     * @return the value of notice.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.gmt_modified
     *
     * @param gmtModified the value for notice.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.is_deleted
     *
     * @return the value of notice.is_deleted
     *
     * @mbg.generated
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.is_deleted
     *
     * @param isDeleted the value for notice.is_deleted
     *
     * @mbg.generated
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.notice_message
     *
     * @return the value of notice.notice_message
     *
     * @mbg.generated
     */
    public String getNoticeMessage() {
        return noticeMessage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.notice_message
     *
     * @param noticeMessage the value for notice.notice_message
     *
     * @mbg.generated
     */
    public void setNoticeMessage(String noticeMessage) {
        this.noticeMessage = noticeMessage == null ? null : noticeMessage.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", noticeId=").append(noticeId);
        sb.append(", noticeTittleName=").append(noticeTittleName);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", noticeMessage=").append(noticeMessage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}