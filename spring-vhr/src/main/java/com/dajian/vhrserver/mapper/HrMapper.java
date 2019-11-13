package com.dajian.vhrserver.mapper;


import com.dajian.vhrserver.pojo.Hr;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface HrMapper{

    Hr getUserByUsername(@Param("username") String username);

    void insertHr(@Param("username") String username, @Param("password") String encode);

    List<Hr> getHrsByPage(@Param("start") int start,@Param("rows") Integer rows,@Param("key") String key);

    Hr getHrById(@Param("hrId") Long id);

    void updateHrEnabled(Hr hr);

    void deleteHrById(Long id);
}
