package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.JobLevel;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface JobLevelMapper {

    @Select("SELECT * FROM joblevel WHERE enabled=TRUE ORDER BY id")
    List<JobLevel> getAllJobTitle();

    @Insert("INSERT INTO joblevel (id,name,titleLevel,createDate) VALUES(#{id},#{name},#{titleLevel},#{createDate})")
    void addJobTitle(JobLevel jobLevel);

    @Update("UPDATE joblevel SET name=#{name}, titleLevel=#{titleLevel} WHERE id=#{id}")
    void updateJobTitle(JobLevel jobLevel);

    @Delete("DELETE FROM jobLevel WHERE id=#{id}")
    void deleteJobTitleById(@Param("id") Long id);
}
