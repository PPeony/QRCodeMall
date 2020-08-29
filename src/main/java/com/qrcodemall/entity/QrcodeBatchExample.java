package com.qrcodemall.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QrcodeBatchExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public QrcodeBatchExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andQrcodeBatchIdIsNull() {
            addCriterion("qrcode_batch_id is null");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdIsNotNull() {
            addCriterion("qrcode_batch_id is not null");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdEqualTo(Integer value) {
            addCriterion("qrcode_batch_id =", value, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdNotEqualTo(Integer value) {
            addCriterion("qrcode_batch_id <>", value, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdGreaterThan(Integer value) {
            addCriterion("qrcode_batch_id >", value, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("qrcode_batch_id >=", value, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdLessThan(Integer value) {
            addCriterion("qrcode_batch_id <", value, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("qrcode_batch_id <=", value, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdIn(List<Integer> values) {
            addCriterion("qrcode_batch_id in", values, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdNotIn(List<Integer> values) {
            addCriterion("qrcode_batch_id not in", values, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_batch_id between", value1, value2, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andQrcodeBatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_batch_id not between", value1, value2, "qrcodeBatchId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberIsNull() {
            addCriterion("qrcode_number is null");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberIsNotNull() {
            addCriterion("qrcode_number is not null");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberEqualTo(Integer value) {
            addCriterion("qrcode_number =", value, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberNotEqualTo(Integer value) {
            addCriterion("qrcode_number <>", value, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberGreaterThan(Integer value) {
            addCriterion("qrcode_number >", value, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("qrcode_number >=", value, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberLessThan(Integer value) {
            addCriterion("qrcode_number <", value, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberLessThanOrEqualTo(Integer value) {
            addCriterion("qrcode_number <=", value, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberIn(List<Integer> values) {
            addCriterion("qrcode_number in", values, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberNotIn(List<Integer> values) {
            addCriterion("qrcode_number not in", values, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_number between", value1, value2, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andQrcodeNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_number not between", value1, value2, "qrcodeNumber");
            return (Criteria) this;
        }

        public Criteria andIsMailIsNull() {
            addCriterion("is_mail is null");
            return (Criteria) this;
        }

        public Criteria andIsMailIsNotNull() {
            addCriterion("is_mail is not null");
            return (Criteria) this;
        }

        public Criteria andIsMailEqualTo(Integer value) {
            addCriterion("is_mail =", value, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailNotEqualTo(Integer value) {
            addCriterion("is_mail <>", value, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailGreaterThan(Integer value) {
            addCriterion("is_mail >", value, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_mail >=", value, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailLessThan(Integer value) {
            addCriterion("is_mail <", value, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailLessThanOrEqualTo(Integer value) {
            addCriterion("is_mail <=", value, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailIn(List<Integer> values) {
            addCriterion("is_mail in", values, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailNotIn(List<Integer> values) {
            addCriterion("is_mail not in", values, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailBetween(Integer value1, Integer value2) {
            addCriterion("is_mail between", value1, value2, "isMail");
            return (Criteria) this;
        }

        public Criteria andIsMailNotBetween(Integer value1, Integer value2) {
            addCriterion("is_mail not between", value1, value2, "isMail");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameIsNull() {
            addCriterion("goods_type_name is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameIsNotNull() {
            addCriterion("goods_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameEqualTo(String value) {
            addCriterion("goods_type_name =", value, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameNotEqualTo(String value) {
            addCriterion("goods_type_name <>", value, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameGreaterThan(String value) {
            addCriterion("goods_type_name >", value, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("goods_type_name >=", value, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameLessThan(String value) {
            addCriterion("goods_type_name <", value, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameLessThanOrEqualTo(String value) {
            addCriterion("goods_type_name <=", value, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameLike(String value) {
            addCriterion("goods_type_name like", value, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameNotLike(String value) {
            addCriterion("goods_type_name not like", value, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameIn(List<String> values) {
            addCriterion("goods_type_name in", values, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameNotIn(List<String> values) {
            addCriterion("goods_type_name not in", values, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameBetween(String value1, String value2) {
            addCriterion("goods_type_name between", value1, value2, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNameNotBetween(String value1, String value2) {
            addCriterion("goods_type_name not between", value1, value2, "goodsTypeName");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIsNull() {
            addCriterion("qrcode_id is null");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIsNotNull() {
            addCriterion("qrcode_id is not null");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdEqualTo(Integer value) {
            addCriterion("qrcode_id =", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotEqualTo(Integer value) {
            addCriterion("qrcode_id <>", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdGreaterThan(Integer value) {
            addCriterion("qrcode_id >", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("qrcode_id >=", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdLessThan(Integer value) {
            addCriterion("qrcode_id <", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdLessThanOrEqualTo(Integer value) {
            addCriterion("qrcode_id <=", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIn(List<Integer> values) {
            addCriterion("qrcode_id in", values, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotIn(List<Integer> values) {
            addCriterion("qrcode_id not in", values, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_id between", value1, value2, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_id not between", value1, value2, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIsNull() {
            addCriterion("express_number is null");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIsNotNull() {
            addCriterion("express_number is not null");
            return (Criteria) this;
        }

        public Criteria andExpressNumberEqualTo(String value) {
            addCriterion("express_number =", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotEqualTo(String value) {
            addCriterion("express_number <>", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberGreaterThan(String value) {
            addCriterion("express_number >", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberGreaterThanOrEqualTo(String value) {
            addCriterion("express_number >=", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLessThan(String value) {
            addCriterion("express_number <", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLessThanOrEqualTo(String value) {
            addCriterion("express_number <=", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLike(String value) {
            addCriterion("express_number like", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotLike(String value) {
            addCriterion("express_number not like", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIn(List<String> values) {
            addCriterion("express_number in", values, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotIn(List<String> values) {
            addCriterion("express_number not in", values, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberBetween(String value1, String value2) {
            addCriterion("express_number between", value1, value2, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotBetween(String value1, String value2) {
            addCriterion("express_number not between", value1, value2, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyIsNull() {
            addCriterion("express_services_company is null");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyIsNotNull() {
            addCriterion("express_services_company is not null");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyEqualTo(String value) {
            addCriterion("express_services_company =", value, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyNotEqualTo(String value) {
            addCriterion("express_services_company <>", value, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyGreaterThan(String value) {
            addCriterion("express_services_company >", value, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("express_services_company >=", value, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyLessThan(String value) {
            addCriterion("express_services_company <", value, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyLessThanOrEqualTo(String value) {
            addCriterion("express_services_company <=", value, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyLike(String value) {
            addCriterion("express_services_company like", value, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyNotLike(String value) {
            addCriterion("express_services_company not like", value, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyIn(List<String> values) {
            addCriterion("express_services_company in", values, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyNotIn(List<String> values) {
            addCriterion("express_services_company not in", values, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyBetween(String value1, String value2) {
            addCriterion("express_services_company between", value1, value2, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andExpressServicesCompanyNotBetween(String value1, String value2) {
            addCriterion("express_services_company not between", value1, value2, "expressServicesCompany");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdIsNull() {
            addCriterion("user_address_id is null");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdIsNotNull() {
            addCriterion("user_address_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdEqualTo(Integer value) {
            addCriterion("user_address_id =", value, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdNotEqualTo(Integer value) {
            addCriterion("user_address_id <>", value, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdGreaterThan(Integer value) {
            addCriterion("user_address_id >", value, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_address_id >=", value, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdLessThan(Integer value) {
            addCriterion("user_address_id <", value, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_address_id <=", value, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdIn(List<Integer> values) {
            addCriterion("user_address_id in", values, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdNotIn(List<Integer> values) {
            addCriterion("user_address_id not in", values, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdBetween(Integer value1, Integer value2) {
            addCriterion("user_address_id between", value1, value2, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andUserAddressIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_address_id not between", value1, value2, "userAddressId");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNull() {
            addCriterion("gmt_created is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNotNull() {
            addCriterion("gmt_created is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedEqualTo(Date value) {
            addCriterion("gmt_created =", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotEqualTo(Date value) {
            addCriterion("gmt_created <>", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThan(Date value) {
            addCriterion("gmt_created >", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_created >=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThan(Date value) {
            addCriterion("gmt_created <", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_created <=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIn(List<Date> values) {
            addCriterion("gmt_created in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotIn(List<Date> values) {
            addCriterion("gmt_created not in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedBetween(Date value1, Date value2) {
            addCriterion("gmt_created between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_created not between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdIsNull() {
            addCriterion("goods_type_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdIsNotNull() {
            addCriterion("goods_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdEqualTo(Integer value) {
            addCriterion("goods_type_id =", value, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdNotEqualTo(Integer value) {
            addCriterion("goods_type_id <>", value, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdGreaterThan(Integer value) {
            addCriterion("goods_type_id >", value, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_type_id >=", value, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdLessThan(Integer value) {
            addCriterion("goods_type_id <", value, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("goods_type_id <=", value, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdIn(List<Integer> values) {
            addCriterion("goods_type_id in", values, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdNotIn(List<Integer> values) {
            addCriterion("goods_type_id not in", values, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("goods_type_id between", value1, value2, "goodsTypeId");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_type_id not between", value1, value2, "goodsTypeId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table qrcode_batch
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table qrcode_batch
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}