package com.dajian.vhrserver.controller.salary;

import com.dajian.vhrserver.pojo.EmployeeBo;
import com.dajian.vhrserver.pojo.PageResult;
import com.dajian.vhrserver.service.EmployeeService;
import com.dajian.vhrserver.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private EmployeeService employeeService;
    @PutMapping
    public ResponseEntity<Void> updateEmployeeSalary(@RequestParam("eid")Long eid, @RequestParam("sid")Long sid){
        salaryService.updateEmployeeSalary(eid,sid);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResult<EmployeeBo>> getCfgData(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows
            ){
        PageResult<EmployeeBo> result = employeeService.getCfgData(key,page,rows);
        return ResponseEntity.ok(result);
    }
}
