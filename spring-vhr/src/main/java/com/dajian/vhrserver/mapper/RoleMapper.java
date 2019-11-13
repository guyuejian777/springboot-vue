package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {
    @Select("SELECT r.* FROM hr_role h_r, role r WHERE r.id=h_r.rid AND h_r.hrid=#{hrId}")
    List<Role> getRolesByHrId(@Param("hrId") Long hrId);

    @Select("SELECT r.* FROM menu_role m_r, role r WHERE r.id=m_r.rid AND m_r.mid=#{menuId}")
    List<Role> getRolesByMenuId(@Param("menuId") Long menuId);

    @Select("SELECT * FROM role WHERE id!=6")
    List<Role> getAllRoles();

    @Insert("INSERT INTO role (id, name, nameZh) VALUES(#{id}, #{name},#{nameZh})")
    void addRole(Role role);


    @Select("SELECT * FROM role WHERE nameZh=#{nameZh}")
    Role getRoleByNameZh(@Param("nameZh") String nameZh);

    @Delete("DELETE FROM role WHERE id=#{id}")
    void deleteById(@Param("id") Long id);

    @Update("UPDATE role SET name=#{name},nameZh=#{nameZh} WHERE id=#{id}")
    void updateRole(Role role);
}
