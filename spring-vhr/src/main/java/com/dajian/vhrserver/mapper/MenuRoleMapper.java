package com.dajian.vhrserver.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface MenuRoleMapper {

    @Insert("INSERT INTO menu_role (mid, rid) VALUES (#{mid},#{rid})")
    void addRole2Menu( @Param("mid") Long mid,@Param("rid") Long rid);

    @Delete("DELETE FROM menu_role WHERE rid=#{rid}")
    void deleteByRid(@Param("rid") Long rid);
}
