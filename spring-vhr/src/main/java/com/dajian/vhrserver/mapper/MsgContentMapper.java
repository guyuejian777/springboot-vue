package com.dajian.vhrserver.mapper;

import com.dajian.vhrserver.pojo.MsgContent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MsgContentMapper {

    @Select("SELECT * FROM msgcontent WHERE id=#{id}")
    public MsgContent getMsgContent(Long id);
}
