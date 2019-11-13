package com.dajian.vhrserver.controller.employee;

import com.dajian.vhrserver.mapper.EmployeeMapper;
import com.dajian.vhrserver.mapper.PositionMapper;
import com.dajian.vhrserver.pojo.EmployeeBo;
import com.dajian.vhrserver.pojo.PageResult;
import com.dajian.vhrserver.pojo.Position;
import com.dajian.vhrserver.service.EmployeeService;
import com.dajian.vhrserver.utils.EmailUtil;
import com.dajian.vhrserver.utils.poi.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private ExecutorService executorService; //线程池
    @Value("${spring.mail.username}")
    private String from;    //发送邮件人的地址
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private PositionMapper positionMapper;

    @DeleteMapping("{ids}")
    public ResponseEntity<Void> deleteEmployeeByIds(@PathVariable("ids") List<Long> ids) {
        employeeService.deleteEmployeeByIds(ids);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addEmployee(@RequestBody EmployeeBo employee) {
        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }
        employeeService.saveEmployee(employee);
        List<Position> allPosition = positionMapper.getAllPosition();
        for (Position position: allPosition){
            if (position.getId() == employee.getId()){
                employee.setPosName(position.getName());
            }
        }
        executorService.execute(
                new EmailUtil(employee, mailSender, from, templateEngine, "xx公司通知"));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeBo employee) {
        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }
        employeeService.updateEmployee(employee);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据最大ID的workID生成新的workID
     *
     * @return
     */
    @GetMapping("/workID")
    public ResponseEntity<String> getWorkId() {
        String workId = employeeService.getWorkID();
        return ResponseEntity.ok(workId);
    }

    /**
     * 根据条件进行基本和高级查询
     *
     * @param key            默认搜索关键字
     * @param page           默认当前页面
     * @param rows           默认显示多少条
     * @param sortBy         根据查询
     * @param desc           升序还降序 asc:升序
     * @param nationId       民族ID
     * @param politicId      政治面貌ID
     * @param departmentId   部门
     * @param jobLevelId     职称ID
     * @param posId          职位ID
     * @param engageForm     聘用形式
     * @param beginDateScope 入职时间 yyyy-MM-dd,yyyy-MM-dd
     * @return 返回分页结果集
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<EmployeeBo>> queryEmployeesByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc,
            @RequestParam(value = "nationId", required = false) Long nationId,
            @RequestParam(value = "politicId", required = false) Long politicId,
            @RequestParam(value = "departmentId", required = false) Long departmentId,
            @RequestParam(value = "jobLevelId", required = false) Long jobLevelId,
            @RequestParam(value = "posId", required = false) Long posId,
            @RequestParam(value = "engageForm", required = false) String engageForm,
            @RequestParam(value = "beginDateScope", required = false) String beginDateScope
    ) {
        PageResult<EmployeeBo> result = employeeService.queryEmployeesByPage(
                key, page, rows, sortBy, desc, nationId, politicId, departmentId, jobLevelId, posId, engageForm, beginDateScope);
//        if (result== null || CollectionUtils.isEmpty(result.getItems())){
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/Options")
    public ResponseEntity<Map<String, Object>> queryOptions() {

        Map<String, Object> map = employeeService.queryOptions();
        if (CollectionUtils.isEmpty(map)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(map);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importEmployee(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return ResponseEntity.ok("上传失败，参数不合法");
        }
        List<List<Object>> bankListByExcel = ExcelUtil.getBankListByExcel(file.getInputStream(), file.getOriginalFilename());
        List<EmployeeBo> employees = employeeService.importEmployee(bankListByExcel);

        employeeService.saveEmployees(employees);
        return ResponseEntity.ok("上传成功！");
    }

    @GetMapping("/export")
    public ResponseEntity<Void> export(HttpServletResponse response) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, ParseException, InvocationTargetException, UnsupportedEncodingException {
        System.out.println("excel导出开始");
        String salaryDate = "1";
        System.out.println("list:" + salaryDate);
        if (salaryDate != "") {
            response.reset(); //清除buffer缓存
            Map<String, Object> map = new HashMap<String, Object>();
            System.out.println("文件名：");
            // 指定下载的文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + salaryDate + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            HSSFWorkbook workbook = null;
            //导出Excel对象
            workbook = employeeService.exportExcelInfo(salaryDate);
            OutputStream output;
            try {
                output = response.getOutputStream();
                BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
                bufferedOutPut.flush();
                workbook.write(bufferedOutPut);
                bufferedOutPut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
