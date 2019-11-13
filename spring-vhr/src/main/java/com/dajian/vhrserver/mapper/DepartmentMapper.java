package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {

    List<Department> getAllDepartment();


    List<Department> getDepartmentByPid(@Param("pid") Long pid);


    Long saveDepartment(Department department);

    String getParentDepartmentDePath(@Param("pid") Long pid);

    void updateParentDepParentId(@Param("pid") Long pid);

    void updateDepartment(Department department);

    Department getDepartmentById(@Param("id") Long id);

    void deleteDepartmentById(@Param("id") Long id);
}
