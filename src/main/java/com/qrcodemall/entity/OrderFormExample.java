package com.qrcodemall.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderFormExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table order_form
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table order_form
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table order_form
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
     *
     * @mbg.generated
     */
    public OrderFormExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
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
     * This method corresponds to the database table order_form
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
     * This method corresponds to the database table order_form
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_form
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
     * This class corresponds to the database table order_form
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

        public Criteria andOrderFormIdIsNull() {
            addCriterion("order_form_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdIsNotNull() {
            addCriterion("order_form_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdEqualTo(Integer value) {
            addCriterion("order_form_id =", value, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdNotEqualTo(Integer value) {
            addCriterion("order_form_id <>", value, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdGreaterThan(Integer value) {
            addCriterion("order_form_id >", value, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_form_id >=", value, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdLessThan(Integer value) {
            addCriterion("order_form_id <", value, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_form_id <=", value, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdIn(List<Integer> values) {
            addCriterion("order_form_id in", values, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdNotIn(List<Integer> values) {
            addCriterion("order_form_id not in", values, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdBetween(Integer value1, Integer value2) {
            addCriterion("order_form_id between", value1, value2, "orderFormId");
            return (Criteria) this;
        }

        public Criteria andOrderFormIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_form_id not between", value1, value2, "orderFormId");
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

        public Criteria andOrderFormNumberIsNull() {
            addCriterion("order_form_number is null");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberIsNotNull() {
            addCriterion("order_form_number is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberEqualTo(String value) {
            addCriterion("order_form_number =", value, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberNotEqualTo(String value) {
            addCriterion("order_form_number <>", value, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberGreaterThan(String value) {
            addCriterion("order_form_number >", value, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberGreaterThanOrEqualTo(String value) {
            addCriterion("order_form_number >=", value, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberLessThan(String value) {
            addCriterion("order_form_number <", value, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberLessThanOrEqualTo(String value) {
            addCriterion("order_form_number <=", value, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberLike(String value) {
            addCriterion("order_form_number like", value, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberNotLike(String value) {
            addCriterion("order_form_number not like", value, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberIn(List<String> values) {
            addCriterion("order_form_number in", values, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberNotIn(List<String> values) {
            addCriterion("order_form_number not in", values, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberBetween(String value1, String value2) {
            addCriterion("order_form_number between", value1, value2, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormNumberNotBetween(String value1, String value2) {
            addCriterion("order_form_number not between", value1, value2, "orderFormNumber");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusIsNull() {
            addCriterion("order_form_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusIsNotNull() {
            addCriterion("order_form_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusEqualTo(Integer value) {
            addCriterion("order_form_status =", value, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusNotEqualTo(Integer value) {
            addCriterion("order_form_status <>", value, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusGreaterThan(Integer value) {
            addCriterion("order_form_status >", value, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_form_status >=", value, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusLessThan(Integer value) {
            addCriterion("order_form_status <", value, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_form_status <=", value, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusIn(List<Integer> values) {
            addCriterion("order_form_status in", values, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusNotIn(List<Integer> values) {
            addCriterion("order_form_status not in", values, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_form_status between", value1, value2, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_form_status not between", value1, value2, "orderFormStatus");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceIsNull() {
            addCriterion("order_form_price is null");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceIsNotNull() {
            addCriterion("order_form_price is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceEqualTo(Long value) {
            addCriterion("order_form_price =", value, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceNotEqualTo(Long value) {
            addCriterion("order_form_price <>", value, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceGreaterThan(Long value) {
            addCriterion("order_form_price >", value, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceGreaterThanOrEqualTo(Long value) {
            addCriterion("order_form_price >=", value, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceLessThan(Long value) {
            addCriterion("order_form_price <", value, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceLessThanOrEqualTo(Long value) {
            addCriterion("order_form_price <=", value, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceIn(List<Long> values) {
            addCriterion("order_form_price in", values, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceNotIn(List<Long> values) {
            addCriterion("order_form_price not in", values, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceBetween(Long value1, Long value2) {
            addCriterion("order_form_price between", value1, value2, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPriceNotBetween(Long value1, Long value2) {
            addCriterion("order_form_price not between", value1, value2, "orderFormPrice");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeIsNull() {
            addCriterion("order_form_pay_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeIsNotNull() {
            addCriterion("order_form_pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeEqualTo(Integer value) {
            addCriterion("order_form_pay_type =", value, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeNotEqualTo(Integer value) {
            addCriterion("order_form_pay_type <>", value, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeGreaterThan(Integer value) {
            addCriterion("order_form_pay_type >", value, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_form_pay_type >=", value, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeLessThan(Integer value) {
            addCriterion("order_form_pay_type <", value, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeLessThanOrEqualTo(Integer value) {
            addCriterion("order_form_pay_type <=", value, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeIn(List<Integer> values) {
            addCriterion("order_form_pay_type in", values, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeNotIn(List<Integer> values) {
            addCriterion("order_form_pay_type not in", values, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeBetween(Integer value1, Integer value2) {
            addCriterion("order_form_pay_type between", value1, value2, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("order_form_pay_type not between", value1, value2, "orderFormPayType");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeIsNull() {
            addCriterion("order_form_pay_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeIsNotNull() {
            addCriterion("order_form_pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeEqualTo(Date value) {
            addCriterion("order_form_pay_time =", value, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeNotEqualTo(Date value) {
            addCriterion("order_form_pay_time <>", value, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeGreaterThan(Date value) {
            addCriterion("order_form_pay_time >", value, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("order_form_pay_time >=", value, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeLessThan(Date value) {
            addCriterion("order_form_pay_time <", value, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("order_form_pay_time <=", value, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeIn(List<Date> values) {
            addCriterion("order_form_pay_time in", values, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeNotIn(List<Date> values) {
            addCriterion("order_form_pay_time not in", values, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeBetween(Date value1, Date value2) {
            addCriterion("order_form_pay_time between", value1, value2, "orderFormPayTime");
            return (Criteria) this;
        }

        public Criteria andOrderFormPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("order_form_pay_time not between", value1, value2, "orderFormPayTime");
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

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Integer value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Integer value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Integer value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Integer value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Integer> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Integer> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table order_form
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
     * This class corresponds to the database table order_form
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