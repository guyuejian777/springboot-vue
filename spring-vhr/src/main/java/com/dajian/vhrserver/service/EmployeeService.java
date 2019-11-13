package com.dajian.vhrserver.service;

import com.dajian.vhrserver.mapper.*;
import com.dajian.vhrserver.pojo.*;
import com.dajian.vhrserver.utils.poi.ExcelBean;
import com.dajian.vhrserver.utils.poi.ExcelClass;
import com.dajian.vhrserver.utils.poi.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dajian.vhrserver.utils.DateUtil.getDiffDays;
import static com.dajian.vhrserver.utils.DateUtil.parseDate;

@Service
public class EmployeeService {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
    DecimalFormat df = new DecimalFormat("#.##");


    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private JobLevelMapper jobLevelMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private NationMapper nationMapper;
    @Autowired
    private PoliticMapper politicMapper;
    @Autowired
    private SalaryMapper salaryMapper;


    /**
     * @param key   默认搜索关键字
     * @param page  默认当前页面
     * @param rows  默认显示多少条
     * @param sortBy    根据查询
     * @param desc  升序还降序 asc:升序
     * @param nationId 民族ID
     * @param politicId 政治面貌ID
     * @param departmentId 部门
     * @param jobLevelId 职称ID
     * @param posId 职位ID
     * @param engageForm 聘用形式
     * @param beginDateScope 入职时间 yyyy-MM-dd,yyyy-MM-dd
     * @return 返回分页结果集
     */
    public PageResult<EmployeeBo> queryEmployeesByPage(
            String key, Integer page, Integer rows, String sortBy, Boolean desc, Long nationId, Long politicId, Long departmentId, Long jobLevelId, Long posId, String engageForm, String beginDateScope) {
        int start = (page - 1) * rows;
        Date beginWorkDate = null;
        Date endWorkDate = null;
        if (beginDateScope!= null && beginDateScope.contains(",")){
            String[] split = beginDateScope.split(",");
            beginWorkDate=parseDate(split[0]);
            endWorkDate=parseDate(split[1]);
        }
        List<EmployeeBo> employeeBos = employeeMapper.getEmployeesByPage(key, start, rows, sortBy, desc,nationId,politicId,departmentId,jobLevelId,posId,engageForm,beginWorkDate,endWorkDate);
        Long total = employeeMapper.getTotal(key,nationId,politicId,departmentId,jobLevelId,posId,engageForm,beginWorkDate,endWorkDate);
        PageResult<EmployeeBo> result = new PageResult<>(total, employeeBos);

        return result;
    }

    public Map<String, Object> queryOptions() {
        Map<String, Object> map = new HashMap<>();
        List<Nation> nations = nationMapper.getAllNation();
        List<Politic> politics = politicMapper.getAllPolitic();
        List<Department> departments = departmentMapper.getDepartmentByPid(Long.valueOf(-1));
        List<JobLevel> jobLevels = jobLevelMapper.getAllJobTitle();
        List<Position> positions = positionMapper.getAllPosition();

        map.put("nations",nations);
        map.put("politics",politics);
        map.put("departments",departments);
        map.put("jobLevels",jobLevels);
        map.put("positions",positions);
        return map;
    }

    public String getWorkID() {
        //根据最大id的workId得到目前最大的workId
        Long workID = employeeMapper.getMaxWorkID();
        System.out.println("workId-1: "+workID);
        if (workID!=null){ //如果workId存在
            return String.format("%08d",workID+1);
        }else{  //如果不存在
            return String.format("%08d",0);
        }
    }

    /**
     *
     * @param employee
     */
    public void saveEmployee(EmployeeBo employee) {
        employee.setId(null);
        Date begin = employee.getBeginContract();
        Date end = employee.getEndContract();
        Double contractTerm = getDiffDays(begin, end);
        employee.setContractTerm(contractTerm);
        employeeMapper.saveEmployee(employee);
    }

    public void updateEmployee(EmployeeBo employee) {
        employeeMapper.updateEmployee(employee);
    }

    public HSSFWorkbook exportExcelInfo(String salaryDate) throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException, UnsupportedEncodingException {
        //根据条件查询数据，把数据装载到一个list中
        List<EmployeeBo> list =employeeMapper.getEmployeesByPage(
                null,null,null,null,null,null,null,null,null,null,null,null,null);
        List<ExcelBean> excel=new ArrayList<>();
        Map<Integer,List<ExcelBean>> map=new ExcelClass().contentExcel();
        HSSFWorkbook hssfWorkbook=null;

        String sheetName = "xx公司"+salaryDate;
        //调用ExcelUtil的方法
        hssfWorkbook = ExcelUtil.createExcelFile(EmployeeBo.class, list, map, sheetName);
        return hssfWorkbook;
    }



    public List<EmployeeBo> importEmployee(List<List<Object>> bankListByExcel) throws ParseException {
        List<Nation> nations = nationMapper.getAllNation();
        List<Politic> politics = politicMapper.getAllPolitic();
        List<Department> departments = departmentMapper.getAllDepartment();
        List<JobLevel> jobLevels = jobLevelMapper.getAllJobTitle();
        List<Position> positions = positionMapper.getAllPosition();

        List<EmployeeBo> employees = new ArrayList<>();
        for (List<Object> one: bankListByExcel){
            EmployeeBo employeeBo = new EmployeeBo();
            employeeBo.setId(Long.valueOf(one.get(0).toString()));
            employeeBo.setName((String) one.get(1));
            employeeBo.setWorkID((String) one.get(2));
            employeeBo.setGender((String) one.get(3));
            employeeBo.setBirthday(dateFormat1.parse(one.get(4).toString()) );
            employeeBo.setIdCard((String) one.get(5));
            employeeBo.setWedlock((String) one.get(6));

            String nationName = one.get(7).toString();
            for (Nation nation: nations){
                if (nationName.equals(nation.getName())){
                    employeeBo.setNationId(nation.getId());
                }
            }

            employeeBo.setNativePlace((String) one.get(8));

            for (Politic i: politics){
                if (one.get(9).toString().equals(i.getName())){
                    employeeBo.setPoliticId(i.getId());
                }
            }
            employeeBo.setEmail((String) one.get(10));
            employeeBo.setPhone((String) one.get(11));
            employeeBo.setAddress((String) one.get(12));

            for (Department i: departments){
                if (one.get(13).toString().equals(i.getName())){
                    employeeBo.setDepartmentId(i.getId());
                }
            }
            for (JobLevel i: jobLevels){
                if (one.get(14).toString().equals(i.getName())){
                    employeeBo.setJobLevelId(i.getId());
                }
            }
            for (Position i: positions){
                if (one.get(15).toString().equals(i.getName())){
                    employeeBo.setPosId(i.getId());
                }
            }
            employeeBo.setEngageForm((String) one.get(16));
            employeeBo.setTiptopDegree((String) one.get(17));
            employeeBo.setSpecialty((String) one.get(18));
            employeeBo.setSchool((String) one.get(19));
            Date beginDate = (one.get(20) == null || one.get(20).toString() == "" ? null : dateFormat1.parse(one.get(20).toString()));
            employeeBo.setBeginDate(beginDate);
            employeeBo.setWorkState((String) one.get(21));
            employeeBo.setContractTerm(Double.valueOf(one.get(22).toString()) );

            Date beginContract = (one.get(23) == null || one.get(23).toString() == "" ? null :dateFormat1.parse(one.get(23).toString()));
            employeeBo.setBeginContract(beginContract);
            Date endContract = (one.get(24) == null || one.get(24).toString() == "" ? null : dateFormat1.parse(one.get(24).toString()));
            employeeBo.setEndContract(endContract);
            employees.add(employeeBo);
        }
        return employees;
    }

    public void saveEmployees(List<EmployeeBo> employees) {
//        employees.forEach(employeeBo -> {
//            employeeMapper.saveEmployee(employeeBo);
//        });
        System.out.println("employee.size()===>: "+employees.size());
        for (int i=0; i<employees.size(); i++){
            employeeMapper.saveEmployee(employees.get(i));
        }
    }

    public void deleteEmployeeByIds(List<Long> ids) {
        ids.forEach(id -> {
            employeeMapper.deleteEmployeeById(id);
        });
    }


    public PageResult<EmployeeBo> getCfgData(String key, Integer page, Integer rows) {
        Integer start = (page -1)*rows;
        List<EmployeeBo> employees = employeeMapper.getCfgData(start,rows,key);
        Long total = employeeMapper.getTotal(key,null,null,null,null,null,null,null,null);
        PageResult<EmployeeBo> result = new PageResult<>(total, employees);
        return result;
    }
}
