package com.qrcodemall.dao;

import com.qrcodemall.entity.Property;
import com.qrcodemall.entity.PropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PropertyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    long countByExample(PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    int deleteByExample(PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer propertyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    int insert(Property record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    int insertSelective(Property record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    List<Property> selectByExample(PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    Property selectByPrimaryKey(Integer propertyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Property record, @Param("example") PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Property record, @Param("example") PropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Property record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table property
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Property record);
}