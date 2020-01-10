package com.example.demo.dao;

import com.example.demo.entity.Drugs;
import com.example.demo.entity.Enter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnterDao {

    List<Drugs> getNameLike(String name);

    List<Drugs> getManufacturerLike(String name);

    Integer selectStock(String name);

    int updateStockById(@Param("stock")Integer stock,@Param("name")String name);

    int addEnter(@Param("name") String name, @Param("quantityWarehousing") Integer quantityWarehousing,@Param("informationStock")Integer informationStock,@Param("provider") String provider,@Param("operator") String operator,@Param("price") double price);

    List<Enter> selectEnterAll();

    List<Enter> getOperatorLike(String name);

}
