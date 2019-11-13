package com.dajian.vhrserver.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface HrRoleMapper {

    @Delete("DELETE FROM hr_role WHERE hrid=#{hrId}")
    void deleteHrRole(@Param("hrId") Long hrId);

    @Insert("INSERT INTO hr_role (hrid,rid) VALUES(#{hrId},#{rid})")
    void saveHrRole(@Param("hrId")Long hrId, @Param("rid")Long rid);


    @Delete("DELETE FROM hr_role WHERE hrid=#{hrId} AND rid=#{rid}")
    void deleteHrOneRole(@Param("hrId") Long hrId,@Param("rid") Long rid);
}
