package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.Politic;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PoliticMapper {
    @Select("SELECT * FROM politicsstatus")
    List<Politic> getAllPolitic();
}
