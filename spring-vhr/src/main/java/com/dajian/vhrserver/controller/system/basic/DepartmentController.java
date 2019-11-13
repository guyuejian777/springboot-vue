package com.dajian.vhrserver.controller.system.basic;

import com.dajian.vhrserver.pojo.Department;
import com.dajian.vhrserver.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basicMsg/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartment(){
        List<Department> departments = departmentService.getAllDepartment();
        if (CollectionUtils.isEmpty(departments)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(departments);
    }

    @GetMapping("{pid}")
    public ResponseEntity<List<Department>> getDepartmentByPid(@PathVariable("pid") Long pid){
        List<Department> departments = departmentService.getDepartmentByPid(pid);
        if (CollectionUtils.isEmpty(departments)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(departments);
    }

    @PostMapping
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department){
        Department result = departmentService.saveDepartment(department);
        return new ResponseEntity(result,HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable("id") Long id){
        int i = departmentService.deleteDepartmentById(id);
        if (i== 0){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
        return ResponseEntity.ok().build();
    }
}
