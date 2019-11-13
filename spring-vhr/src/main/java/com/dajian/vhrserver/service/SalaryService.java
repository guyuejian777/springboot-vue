package com.dajian.vhrserver.service;

import com.dajian.vhrserver.mapper.EmployeeSalaryMapper;
import com.dajian.vhrserver.mapper.SalaryMapper;
import com.dajian.vhrserver.pojo.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryMapper salaryMapper;
    @Autowired
    private EmployeeSalaryMapper employeeSalaryMapper;

    public void saveSalary(Salary salary) {
        salaryMapper.addSalary(salary);
    }

    public void updateSalary(Salary salary) {
        salaryMapper.updateSalary(salary);
    }

    public void deleteSalaryById(List<Long> ids) {
        ids.forEach(id -> {
            salaryMapper.deleteSalaryById(id);
        });
    }
    public void updateEmployeeSalary(Long eid, Long sid) {
        employeeSalaryMapper.deleteEmployeeSalary(eid);
        employeeSalaryMapper.addEmployeeSalary(eid,sid);
    }

    public List<Salary> getSalies() {
        List<Salary> salaries = salaryMapper.getAllSalary();
        return salaries;
    }
}
