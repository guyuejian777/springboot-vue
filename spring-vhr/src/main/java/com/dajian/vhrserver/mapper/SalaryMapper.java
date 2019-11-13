package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.Salary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalaryMapper {
    List<Salary> getAllSalary();

    void addSalary(Salary salary);

    void updateSalary(Salary salary);

    void deleteSalaryById(@Param("id") Long id);


}
