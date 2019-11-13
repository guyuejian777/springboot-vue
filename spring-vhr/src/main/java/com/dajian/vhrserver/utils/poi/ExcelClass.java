package com.dajian.vhrserver.utils.poi;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel表格对应的表头信息处理类
 * Created by ASUS on 2018/6/11
 *
 * @Authod Grey Wolf
 */
public class ExcelClass {

    public  Map<Integer,List<ExcelBean>> contentExcel(){
        List<ExcelBean> excel=new ArrayList<>();
        Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook=null;
        //设置标题栏

        excel.add(new ExcelBean("员工编号","id",0));
        excel.add(new ExcelBean("员工姓名","name",0));
        excel.add(new ExcelBean("工号","workID",0));
        excel.add(new ExcelBean("性别","gender",0));
        excel.add(new ExcelBean("出生日期","birthday",0));
        excel.add(new ExcelBean("身份证号","idCard",0));
        excel.add(new ExcelBean("婚姻状况","wedlock",0));
        excel.add(new ExcelBean("民族","nationName",0));
        excel.add(new ExcelBean("籍贯","nativePlace",0));
        excel.add(new ExcelBean("政治面貌","politicName",0));
        excel.add(new ExcelBean("邮箱","email",0));
        excel.add(new ExcelBean("电话号码","phone",0));
        excel.add(new ExcelBean("联系地址","address",0));
        excel.add(new ExcelBean("所属部门","departmentName",0));
        excel.add(new ExcelBean("职称","jobLevelName",0));
        excel.add(new ExcelBean("职位","posName",0));
        excel.add(new ExcelBean("聘用形式","engageForm",0));
        excel.add(new ExcelBean("最高学历","tiptopDegree",0));
        excel.add(new ExcelBean("所属专业","specialty",0));
        excel.add(new ExcelBean("毕业院校","school",0));
        excel.add(new ExcelBean("入职日期","beginDate",0));
        excel.add(new ExcelBean("在职状态","workState",0));
        excel.add(new ExcelBean("合同期限","contractTerm",0));
        excel.add(new ExcelBean("合同起始日期","beginContract",0));
        excel.add(new ExcelBean("合同终止日期","endContract",0));
        map.put(0, excel);
        return map;
    }

}
