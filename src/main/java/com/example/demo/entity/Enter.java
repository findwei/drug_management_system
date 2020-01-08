package com.example.demo.entity;

import lombok.Data;

@Data
public class Enter {
    private Integer id;
    private String name;                       //药品名称
    private Integer quantityWarehousing;       //入库数量
    private Integer informationStock;         //药品库存
    private String provider;                   //提供商
    private String operator;                   //操作人
    private String createTime;                 //操作时间
    private double price;                      //价格

}
