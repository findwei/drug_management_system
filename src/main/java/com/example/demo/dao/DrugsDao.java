package com.example.demo.dao;

import com.example.demo.entity.Drugs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DrugsDao {

    int addDrugs(@Param("name") String name,@Param("category") String category,@Param("specifications") String specifications,@Param("manufacturer") String manufacturer,@Param("price") double price,@Param("stock") Integer stock);

    Drugs getDrugsById(Integer id);

    int updateDrugsById(@Param("name") String name,@Param("category") String category,@Param("specifications") String specifications,@Param("manufacturer") String manufacturer,@Param("price") double price,@Param("stock") Integer stock,@Param("id")Integer id);

    int deleteDrugsById(Integer id);

    List<Drugs> getNameLike(String name);

    List<Drugs> selectDrugsAll();
}
