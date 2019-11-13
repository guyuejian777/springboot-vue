package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.Employee;
import com.dajian.vhrserver.pojo.EmployeeBo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {


    List<EmployeeBo> getEmployeesByPage(String key, Integer start, Integer rows, String sortBy, Boolean desc, Long nationId, Long politicId, Long departmentId, Long jobLevelId, Long posId, String engageForm, Date beginWorkDate, Date endWorkDate);

    Long getTotal(@Param("key") String key,@Param("nationId") Long nationId,@Param("politicId") Long politicId,@Param("departmentId") Long departmentId,@Param("jobLevelId") Long jobLevelId,@Param("posId") Long posId,@Param("engageForm") String engageForm, @Param("beginWorkDate") Date beginWorkDate, @Param("endWorkDate") Date endWorkDate);

    Long getMaxWorkID();

    void saveEmployee(EmployeeBo employee);

    void updateEmployee(EmployeeBo employee);

    void deleteEmployeeById(@Param("id") Long id);


    List<EmployeeBo> getCfgData(@Param("start") Integer start, @Param("rows") Integer rows,@Param("key") String key);
}
