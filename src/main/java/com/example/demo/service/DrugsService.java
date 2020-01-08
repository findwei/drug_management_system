package com.example.demo.service;

import com.example.demo.entity.PagedResult;

import java.util.Map;

public interface DrugsService {

    Map<String,Object> addDrugs(String name, String category, String specifications, String manufacturer, double price, Integer stock);

    Map<String,Object> getDrugsById(Integer id);

    Map<String,Object> updateDrugsById(String name, String category, String specifications, String manufacturer, double price, Integer stock,Integer id);

    Map<String,Object> deleteDrugsById(Integer id);

    Map<String,Object> getNameLike(String name,Integer page, Integer pageSize);

    PagedResult selectDrugsAll(Integer page, Integer pageSize);

    void generateDrugsTable();
}
