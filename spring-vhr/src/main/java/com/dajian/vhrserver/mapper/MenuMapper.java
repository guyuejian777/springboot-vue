package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    List<Menu> getAllMenu();


    List<Menu> getMenusByHrId(@Param("hrId") Long hrId);


    List<Menu> getMenuTreeData();

    List<Long> getMenusByRid(@Param("rId") Long rid);

    List<Menu> getMenusByPid(@Param("pid") Long pid);
}
