package com.example.demo.controller;

import com.example.demo.entity.PagedResult;
import com.example.demo.service.EnterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/enter")
@Api(value = "入库管理",description = "入库管理")
public class EnterController {
    @Autowired
    private EnterService enterService;

    @GetMapping("/getNameLikeEnter")
    @ApiOperation("添加时模糊查询药品名")
    public Map<String,Object> getDrugsById(@RequestParam(value = "name",required = true) String name){
        return enterService.getNameLike(name);
    }

    @GetMapping("/getManufacturerLike")
    @ApiOperation("添加时模糊查询生产厂家")
    public Map<String,Object> getManufacturerLike(@RequestParam(value = "name",required = true) String name){
        return enterService.getManufacturerLike(name);
    }

    @GetMapping("/addEnter")
    @ApiOperation("添加入库信息")
    public Map<String,Object> addEnter(@RequestParam(value = "name",required = true) String name,@RequestParam(value = "quantityWarehousing",required = true) Integer quantityWarehousing,
                                       @RequestParam(value = "provider",required = true) String provider,@RequestParam(value = "operator",required = true) String operator,@RequestParam(value = "price",required = true) double price){
        return enterService.addEnter(name,quantityWarehousing,provider,operator,price);
    }

    @GetMapping("/selectEnterAll")
    @ApiOperation("入库分页查询")
    public PagedResult selectEnterAll(@RequestParam(value = "page",required = true)Integer page, @RequestParam(value = "pageSize",required = true)Integer pageSize){
        return enterService.selectEnterAll(page,pageSize);
    }

    @GetMapping("/getOperatorLike")
    @ApiOperation("根据操作人模糊查询")
    public Map<String,Object> getOperatorLike(@RequestParam(value = "name",required = true) String name,@RequestParam(value = "page",required = true)Integer page, @RequestParam(value = "pageSize",required = true)Integer pageSize){
        return enterService.getOperatorLike(name, page, pageSize);
    }

    @GetMapping("/generateEnterTable")
    @ApiOperation("导出excel表")
    public String generateEnterTable(){

        String path = enterService.generateEnterTable();
        return path;
    }

}
