package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.Position;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PositionMapper {

    @Select("SELECT * FROM position WHERE enabled=TRUE ORDER BY id")
    List<Position> getAllPosition();

    @Delete("DELETE FROM position WHERE id=#{id}")
    void deleteById(@Param("id") Long id);

    @Insert("INSERT INTO position (id,name,createDate) VALUES(#{id},#{name},#{createDate})")
    void addPosition(Position position);

    @Update("UPDATE position SET `name`=#{name} WHERE id=#{id}")
    void updatePosition(@Param("id") Long id,@Param("name") String name);
}
