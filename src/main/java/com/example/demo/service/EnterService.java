package com.example.demo.service;

import com.example.demo.entity.PagedResult;

import java.util.Map;

public interface EnterService {

    Map<String,Object> getNameLike(String name);

    Map<String,Object> getManufacturerLike(String name);

    Map<String,Object> addEnter(String name,Integer quantityWarehousing,String provider,String operator,double price);

    PagedResult selectEnterAll(Integer page, Integer pageSize);

    Map<String,Object> getOperatorLike(String name,Integer page, Integer pageSize);

    String generateEnterTable();

}
