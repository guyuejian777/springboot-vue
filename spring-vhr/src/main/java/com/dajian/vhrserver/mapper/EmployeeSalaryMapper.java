package com.dajian.vhrserver.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EmployeeSalaryMapper {


    @Select("INSERT INTO emp_salary SET eid=#{eid}, sid=#{sid}")
    void addEmployeeSalary(@Param("eid") Long eid, @Param("sid") Long sid);

    @Delete("DELETE FROM emp_salary WHERE eid=#{eid}")
    void deleteEmployeeSalary(@Param("eid") Long eid);
}
