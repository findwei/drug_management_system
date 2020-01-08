package com.example.demo.entity;

import lombok.Data;

@Data
public class Drugs {
    private Integer id;
    private String name;                    //药品名称
    private String category;                //药品类别
    private String specifications;          //规格
    private String manufacturer;            //生产厂家
    private double price;                   //参考价格
    private Integer stock;                  //药品库存
}
