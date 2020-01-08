package com.example.demo.service.Impl;

import com.example.demo.dao.DrugsDao;
import com.example.demo.entity.Checkout;
import com.example.demo.entity.Drugs;
import com.example.demo.entity.PagedResult;
import com.example.demo.service.DrugsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrugsServiceImpl implements DrugsService {
    @Autowired
    private DrugsDao drugsDao;

    /**
     * 添加药品信息
     * @param name
     * @param category
     * @param specifications
     * @param manufacturer
     * @param price
     * @param stock
     * @return
     */
    public Map<String,Object> addDrugs(String name,String category,String specifications,String manufacturer,double price,Integer stock){
        Map<String,Object> map = new HashMap<String,Object>();
        int a = drugsDao.addDrugs(name,category,specifications,manufacturer,price,stock);
        if(a==1){
            map.put("msg","添加成功");
            map.put("code",200);
            map.put("name",name);
            map.put("category",category);
            map.put("specifications",specifications);
            map.put("manufacturer",manufacturer);
            map.put("price",price);
            map.put("stock",stock);
            return map;
        }else {
            map.put("msg","添加失败");
            map.put("code",201);
            return map;
        }
    }

    /**
     * 根据id查询药品信息
     * @param id
     * @return
     */
    public Map<String,Object> getDrugsById(Integer id){
        Map<String, Object> map = new HashMap<String, Object>();
        Drugs drugs = drugsDao.getDrugsById(id);
        map.put("msg","查询成功");
        map.put("code",200);
        map.put("id",drugs.getId());
        map.put("name",drugs.getName());
        map.put("category",drugs.getCategory());
        map.put("specifications",drugs.getSpecifications());
        map.put("manufacturer",drugs.getManufacturer());
        map.put("price",drugs.getPrice());
        map.put("stock",drugs.getStock());
        return map;
    }

    /**
     * 根据id修改药品信息
     * @param name
     * @param category
     * @param specifications
     * @param manufacturer
     * @param price
     * @param stock
     * @param id
     * @return
     */
    public Map<String,Object> updateDrugsById(String name,String category,String specifications,String manufacturer,double price,Integer stock,Integer id){
        Map<String, Object> map = new HashMap<>();
        int a = drugsDao.updateDrugsById(name,category,specifications,manufacturer,price,stock,id);
        if(a==1){
            map.put("msg","修改成功");
            map.put("code",200);
            map.put("id",id);
            map.put("name",name);
            map.put("category",category);
            map.put("specifications",specifications);
            map.put("manufacturer",manufacturer);
            map.put("price",price);
            map.put("stock",stock);
            return map;
        }else {
            map.put("msg","修改失败");
            map.put("code",201);
            map.put("data","没有此用户");
            return map;
        }
    }

    /**
     * 根据id删除药品信息
     * @param id
     * @return
     */
    public Map<String,Object> deleteDrugsById(Integer id){
        Map<String, Object> map = new HashMap<>();
        int a = drugsDao.deleteDrugsById(id);
        if (a==1){
            map.put("msg","删除成功");
            map.put("code",200);
            map.put("id",id);
            return map;
        }else {
            map.put("msg","删除失败");
            map.put("code",201);
            map.put("data","没有此用户");
            return map;
        }
    }

    /**
     * 根据药品名称模糊查询
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    public Map<String,Object> getNameLike(String name,Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<Drugs> drugsList = drugsDao.getNameLike(name);
        List<Drugs> drugsListAll = drugsDao.selectDrugsAll();
        if (name.equals("")){
            if(drugsListAll.size() > 0){
                //处理分页
                PageHelper.startPage(page, pageSize);
                //执行分页查询
                List<Drugs> list = drugsDao.selectDrugsAll();
                //返回结果处理
                PageInfo<Drugs> pageList = new PageInfo<>(list);
                PagedResult pagedResult = new PagedResult();
                pagedResult.setPage(page);
                pagedResult.setTotal(pageList.getPages());
                pagedResult.setCode(200);
                pagedResult.setData(list);
                pagedResult.setRecords(pageList.getTotal());
                map.put("msg", "查询成功");
                map.put("data", pagedResult);
                return map;
            }else{
                map.put("msg", "查询失败");
                map.put("code", 201);
                map.put("data", "没有此药品");
                return map;
            }
        }else {
            if (drugsList.size() > 0){
                map.put("msg", "查询成功");
                map.put("code", 200);
                map.put("data", drugsList);
                return map;
            }else{
                map.put("msg", "查询失败");
                map.put("code", 201);
                map.put("data", "没有此药品");
                return map;
            }
        }
    }

    /**
     * 药品分页查询
     * @param page
     * @param pageSize
     * @return
     */
    public PagedResult selectDrugsAll(Integer page, Integer pageSize) {
        //处理分页
        PageHelper.startPage(page, pageSize);
        //执行分页查询
        List<Drugs> list = drugsDao.selectDrugsAll();
        //返回结果处理
        PageInfo<Drugs> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setCode(200);
        pagedResult.setData(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    /**
     * 导出enter表
     */
    public void generateDrugsTable(){
        List<Drugs> drugsList = drugsDao.selectDrugsAll();
        try {
            //1:创建excel文件
            File file=new File("E:/drugs.xls");
            file.createNewFile();
            //2:创建工作簿
            WritableWorkbook workbook2= Workbook.createWorkbook(file);
            //3:创建sheet
            WritableSheet sheet1=workbook2.createSheet("药品信息", 0);
            //4：设置titles
            String[] titles={"编号","药品名称","药品类别","规格","生产厂家","参考价格","药品库存"};
            //5:单元格
            Label label=null;
            //6:给第一行设置列名
            for(int i=0;i<titles.length;i++){
                //x,y,第一行的列名
                label=new Label(i,0,titles[i]);
                //7：添加单元格
                sheet1.addCell(label);
            }
            //8：数据库导入数据
            for(int i=0;i<drugsList.size();i++){
                //添加编号，第二行第一列
                label=new Label(0,i+1,i+1+"");
                sheet1.addCell(label);
                //药品名称
                label=new Label(1,i+1,drugsList.get(i).getName());
                sheet1.addCell(label);
                //药品类别
                label=new Label(2,i+1,drugsList.get(i).getCategory());
                sheet1.addCell(label);
                //规格
                label=new Label(3,i+1,drugsList.get(i).getSpecifications());
                sheet1.addCell(label);
                //生产厂家
                label=new Label(4,i+1,drugsList.get(i).getManufacturer());
                sheet1.addCell(label);
                //参考价格
                label=new Label(5,i+1,drugsList.get(i).getPrice()+"");
                sheet1.addCell(label);
                //药品库存
                label=new Label(6,i+1,drugsList.get(i).getStock()+"");
                sheet1.addCell(label);
            }
            //写入数据，
            workbook2.write();
            //最后一步，关闭工作簿
            workbook2.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }

}
