package com.dajian.vhrserver.service;

import com.dajian.vhrserver.mapper.DepartmentMapper;
import com.dajian.vhrserver.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;


    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment();
    }

    public List<Department> getDepartmentByPid(Long pid) {
        return departmentMapper.getDepartmentByPid(pid);
    }

    /**
     *
     * 1.通过paerentId查询父部门的dePath
     * 2.生成新的dePath
     * 2.通过parentId，name，dePath新建部门
     * 3.把父部门的isParent字段更新为true
     * @param department
     * @return
     */
    @Transactional
    public Department saveDepartment(Department department) {
        String parentDepPath = departmentMapper.getParentDepartmentDePath(department.getParentId());

        department.setId(null);
        department.setEnabled(true);
        departmentMapper.saveDepartment(department);
        String dePath = parentDepPath+"."+department.getId();
        department.setDepPath(dePath);

        departmentMapper.updateDepartment(department);

        departmentMapper.updateParentDepParentId(department.getParentId());
        return department;
    }

    @Transactional
    public int deleteDepartmentById(Long id) {
        Department department = departmentMapper.getDepartmentById(id);
        if (CollectionUtils.isEmpty(department.getChildren()) && department.getParent()){
            return 0;
        }
        departmentMapper.deleteDepartmentById(id);
        //得到父部门信息
        Department parentDepartment = departmentMapper.getDepartmentById(department.getParentId());
        if (CollectionUtils.isEmpty(parentDepartment.getChildren())){
            parentDepartment.setParent(false);
            departmentMapper.updateDepartment(parentDepartment);
        }
        return 1;
    }
}
