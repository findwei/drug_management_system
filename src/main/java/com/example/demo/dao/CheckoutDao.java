package com.example.demo.dao;

import com.example.demo.entity.Checkout;
import com.example.demo.entity.Drugs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckoutDao {

    List<Drugs> getNameLike(String name);

    Integer selectStock(String name);

    int updateStockById(@Param("stock")Integer stock, @Param("name")String name);

    int addCheckout(@Param("name") String name, @Param("outgoingQuantity") Integer outgoingQuantity,@Param("informationStock")Integer informationStock,@Param("operator") String operator,@Param("price") double price);

    List<Checkout> selectCheckoutAll();

    List<Checkout> getOperatorLikeCheckout(String name);

}
