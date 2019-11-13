package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.Nation;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NationMapper {

    @Select("SELECT * FROM nation")
    List<Nation> getAllNation();
}
