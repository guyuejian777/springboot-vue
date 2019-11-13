package com.dajian.vhrserver.controller.salary;

import com.dajian.vhrserver.pojo.Salary;
import com.dajian.vhrserver.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary/sob")
public class SalarySobController {

    @Autowired
    private SalaryService salaryService;
    @GetMapping
    public ResponseEntity<List<Salary>> getAllSalary(){
        List<Salary> salaries = salaryService.getSalies();
        return ResponseEntity.ok(salaries);
    }

    @PostMapping
    public ResponseEntity<Void> saveSalary(@RequestBody Salary salary){
        salaryService.saveSalary(salary);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateSalary(@RequestBody Salary salary){
        salaryService.updateSalary(salary);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{ids}")
    public ResponseEntity<Void> deleteSalaryById(@PathVariable("ids") List<Long> ids){
        salaryService.deleteSalaryById(ids);
        return ResponseEntity.ok().build();
    }


}
