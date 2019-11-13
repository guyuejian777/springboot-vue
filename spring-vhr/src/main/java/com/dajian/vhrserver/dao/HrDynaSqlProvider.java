package com.dajian.vhrserver.dao;

import org.apache.ibatis.jdbc.SQL;

public class HrDynaSqlProvider {

    public String queryHrsByPage(int start,Integer rows, String key) {
        SQL sql = new SQL() {{
            SELECT("h.*, r.id AS rid, r.`name` AS rname, r.nameZh AS rnameZh FROM hr h,  hr_role h_r, role r");
            WHERE(" h.id=h_r.hrid AND r.id=h_r.rid");
            WHERE("h.id NOT IN(SELECT h_r.hrid FROM hr_role h_r, role r WHERE h_r.rid=r.id AND r.nameZh='系统管理员') ");
            if (key != null){
                WHERE("h.`name` like #{key}");
            }
            LIMIT("#{start},#{rows}");
        }};
        return sql.toString();
    }
}
