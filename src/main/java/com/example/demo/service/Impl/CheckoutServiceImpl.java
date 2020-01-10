package com.example.demo.service.Impl;
import com.example.demo.dao.CheckoutDao;
import com.example.demo.entity.Checkout;
import com.example.demo.entity.Drugs;
import com.example.demo.entity.PagedResult;
import com.example.demo.service.CheckoutService;
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
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private CheckoutDao checkoutDao;

    /**
     * 添加时模糊查询药品名
     * @param name
     * @return
     */
    public Map<String,Object> getNameLike(String name){
        Map<String, Object> map = new HashMap<>();
        List<Drugs> drugsList = checkoutDao.getNameLike(name);
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

    /**
     * 添加出库信息
     * @param name
     * @param outgoingQuantity
     * @param operator
     * @param price
     * @return
     */
    public Map<String,Object> addCheckout(String name,Integer outgoingQuantity,String operator,double price){
        Map<String, Object> map = new HashMap<>();
        Integer stock = checkoutDao.selectStock(name);
        checkoutDao.updateStockById(stock-outgoingQuantity,name);
        int a = checkoutDao.addCheckout(name,outgoingQuantity,stock-outgoingQuantity,operator,price);
        if(a==1){
            map.put("msg","添加成功");
            map.put("code",200);
            map.put("name",name);

            map.put("quantityWarehousing",outgoingQuantity);
            map.put("informationStock",stock-outgoingQuantity);
            map.put("operator",operator);
            map.put("price",price);
            return map;
        }else {
            map.put("msg","添加失败");
            map.put("code",201);
            return map;
        }
    }

    /**
     * 出库分页查询
     * @param page
     * @param pageSize
     * @return
     */
    public PagedResult selectCheckoutAll(Integer page, Integer pageSize) {
        //处理分页
        PageHelper.startPage(page, pageSize);
        //执行分页查询
        List<Checkout> list = checkoutDao.selectCheckoutAll();
        for (Checkout checkout:list) {
            String[] str = checkout.getCreateTime().split(" ");
            checkout.setCreateTime(str[0]);
        }
        //返回结果处理
        PageInfo<Checkout> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setCode(200);
        pagedResult.setData(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    /**
     * 根据操作人模糊查询
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    public Map<String,Object> getOperatorLikeCheckout(String name,Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<Checkout> checkoutList = checkoutDao.getOperatorLikeCheckout(name);
        List<Checkout> checkoutListAll = checkoutDao.selectCheckoutAll();
        if (name.equals("")){
            if(checkoutListAll.size() > 0){
                //处理分页
                PageHelper.startPage(page, pageSize);
                //执行分页查询
                List<Checkout> list = checkoutDao.selectCheckoutAll();
                for (Checkout enter:list) {
                    String[] str = enter.getCreateTime().split(" ");
                    enter.setCreateTime(str[0]);
                }
                //返回结果处理
                PageInfo<Checkout> pageList = new PageInfo<>(list);
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
                map.put("data", "没有此操作人");
                return map;
            }
        }else {
            if (checkoutList.size() > 0){
                for (Checkout checkout:checkoutList) {
                    String[] str = checkout.getCreateTime().split(" ");
                    checkout.setCreateTime(str[0]);
                }
                map.put("msg", "查询成功");
                map.put("code", 200);
                map.put("data", checkoutList);
                return map;
            }else{
                map.put("msg", "查询失败");
                map.put("code", 201);
                map.put("data", "没有此操作人");
                return map;
            }
        }
    }

    /**
     * 导出enter表
     */
    public String generateCheckoutTable(){
        List<Checkout> checkoutList = checkoutDao.selectCheckoutAll();
        for (Checkout checkout:checkoutList) {
            String[] str = checkout.getCreateTime().split(" ");
            checkout.setCreateTime(str[0]);
        }
        try {
            //1:创建excel文件
            File file=new File("E:/checkout.xls");
            file.createNewFile();
            //2:创建工作簿
            WritableWorkbook workbook2= Workbook.createWorkbook(file);
            //3:创建sheet
            WritableSheet sheet1=workbook2.createSheet("出库记录", 0);
            //4：设置titles
            String[] titles={"编号","药品名称","出库数量","药品库存","价格","操作人","操作时间"};
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
            for(int i=0;i<checkoutList.size();i++){
                //添加编号，第二行第一列
                label=new Label(0,i+1,i+1+"");
                sheet1.addCell(label);
                //药品名称
                label=new Label(1,i+1,checkoutList.get(i).getName());
                sheet1.addCell(label);
                //入库数量
                label=new Label(2,i+1,checkoutList.get(i).getOutgoingQuantity()+"");
                sheet1.addCell(label);
                //药品库存
                label=new Label(3,i+1,checkoutList.get(i).getInformationStock()+"");
                sheet1.addCell(label);
                //价格
                label=new Label(4,i+1,checkoutList.get(i).getPrice()+"");
                sheet1.addCell(label);
                //操作人
                label=new Label(5,i+1,checkoutList.get(i).getOperator());
                sheet1.addCell(label);
                //操作时间
                label=new Label(6,i+1,checkoutList.get(i).getCreateTime());
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
        return "导出成功";
    }


}
