package com.example.demo.controller;

import com.example.demo.entity.PagedResult;
import com.example.demo.service.CheckoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@Api(value = "出库管理",description = "出库管理")
public class CheckoutController {
    @Autowired



    private CheckoutService checkoutService;


    @GetMapping("/getNameLikeCheckout")
    @ApiOperation("添加时模糊查询药品名")
    public Map<String,Object> getDrugsById(@RequestParam(value = "name",required = true) String name){
        return checkoutService.getNameLike(name);
    }

    @GetMapping("/addCheckout")
    @ApiOperation("添加出库信息")
    public Map<String,Object> addCheckout(@RequestParam(value = "name",required = true) String name,@RequestParam(value = "outgoingQuantity",required = true) Integer outgoingQuantity,
                                       @RequestParam(value = "operator",required = true) String operator,@RequestParam(value = "price",required = true) double price){
        return checkoutService.addCheckout(name,outgoingQuantity,operator,price);
    }

    @GetMapping("/selectCheckoutAll")
    @ApiOperation("出库分页查询")
    public PagedResult selectCheckoutAll(@RequestParam(value = "page",required = true)Integer page, @RequestParam(value = "pageSize",required = true)Integer pageSize){
        return checkoutService.selectCheckoutAll(page,pageSize);
    }

    @GetMapping("/getOperatorLikeCheckout")
    @ApiOperation("根据操作人模糊查询")
    public Map<String,Object> getOperatorLikeCheckout (@RequestParam(value = "name",required = true) String name,@RequestParam(value = "page",required = true)Integer page, @RequestParam(value = "pageSize",required = true)Integer pageSize){
        return checkoutService.getOperatorLikeCheckout(name, page, pageSize);
    }

    @GetMapping("/generateCheckoutTable")
    @ApiOperation("导出excel表")
    public String generateCheckoutTable(){
        String path = checkoutService.generateCheckoutTable();
        return path;
    }

}
