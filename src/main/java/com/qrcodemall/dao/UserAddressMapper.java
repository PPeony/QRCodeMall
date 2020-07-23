package com.qrcodemall.dao;

import com.qrcodemall.entity.UserAddress;
import com.qrcodemall.entity.UserAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    long countByExample(UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    int deleteByExample(UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer userAddressId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    int insert(UserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    int insertSelective(UserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    List<UserAddress> selectByExample(UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    UserAddress selectByPrimaryKey(Integer userAddressId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UserAddress record, @Param("example") UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UserAddress record, @Param("example") UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_address
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserAddress record);
}