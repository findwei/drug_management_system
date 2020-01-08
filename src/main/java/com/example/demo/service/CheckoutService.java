package com.example.demo.service;

import com.example.demo.entity.PagedResult;

import java.util.Map;

public interface CheckoutService {

    Map<String,Object> getNameLike(String name);

    Map<String,Object> addCheckout(Integer id,String name,Integer outgoingQuantity,String operator,double price);

    PagedResult selectCheckoutAll(Integer page, Integer pageSize);

    Map<String,Object> getOperatorLikeCheckout(String name,Integer page, Integer pageSize);

    void generateCheckoutTable();
}
