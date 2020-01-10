package com.example.demo.controller;

import com.example.demo.entity.PagedResult;
import com.example.demo.service.DrugsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/drugs")
@Api(value = "药品管理",description = "药品管理")


public class DrugsController {
    @Autowired
    private DrugsService drugsService;

    @GetMapping("/addDrugs")
    @ApiOperation("添加药品信息")
    public Map<String,Object> addDrugs(@RequestParam(value = "name",required = true) String name,@RequestParam(value = "category",required = true) String category,@RequestParam(value = "specifications",required = true) String specifications,
                                       @RequestParam(value = "manufacturer",required = true) String manufacturer,@RequestParam(value = "price",required = true) double price,@RequestParam(value = "stock",required = true) Integer stock){
        return drugsService.addDrugs(name,category,specifications,manufacturer,price,stock);
    }

    @GetMapping("/getDrugsById")
    @ApiOperation("根据id查询药品信息")
    public Map<String,Object> getDrugsById(@RequestParam(value = "id",required = true) Integer id){
        return drugsService.getDrugsById(id);
    }

    @GetMapping("/updateDrugsById")
    @ApiOperation("根据id修改药品信息")
    public Map<String,Object> updateDrugsById(@RequestParam(value = "name",required = true) String name,@RequestParam(value = "category",required = true) String category,@RequestParam(value = "specifications",required = true) String specifications,
                                              @RequestParam(value = "manufacturer",required = true) String manufacturer,@RequestParam(value = "price",required = true) double price,@RequestParam(value = "stock",required = true) Integer stock,@RequestParam(value = "id",required = true) Integer id){
        return drugsService.updateDrugsById(name,category,specifications,manufacturer,price,stock,id);
    }

    @GetMapping("/deleteDrugsById")
    @ApiOperation("根据id删除药品信息")
    public Map<String,Object> deleteDrugsById(@RequestParam(value = "id",required = true)Integer id){
        return drugsService.deleteDrugsById(id);
    }

    @GetMapping("/selectDrugsAll")
    @ApiOperation("药品分页查询")
    public PagedResult selectDrugsAll(@RequestParam(value = "page",required = true)Integer page, @RequestParam(value = "pageSize",required = true)Integer pageSize){
        return drugsService.selectDrugsAll(page,pageSize);
    }

    @GetMapping("/getNameLike")
    @ApiOperation("根据药品名称模糊查询")
    public Map<String,Object> getNameLike(@RequestParam(value = "name",required = true) String name,@RequestParam(value = "page",required = true)Integer page, @RequestParam(value = "pageSize",required = true)Integer pageSize){
        return drugsService.getNameLike(name, page, pageSize);
    }

    @GetMapping("/generateDrugsTable")
    @ApiOperation("导出excl表")
    public String generateDrugsTable(){

        String path = drugsService.generateDrugsTable();
        return path;
    }

}
