package com.dajian.vhrserver;

import com.dajian.vhrserver.mapper.HrMapper;
import com.dajian.vhrserver.mapper.NationMapper;
import com.dajian.vhrserver.pojo.Hr;
import com.dajian.vhrserver.pojo.Nation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VhrServerApplicationTests {

    @Autowired
    private HrMapper hrMapper;
    @Autowired
    private NationMapper nationMapper;

    @Test
    public void testHrRegistry() {
        Hr hr = new Hr();
        hr.setId(3L);
        hr.setEnabled(true);
        hr.setPassword(new BCryptPasswordEncoder(16).encode("123456"));
        hrMapper.updateHrEnabled(hr);
//        hrMapper.insertHr("root",new BCryptPasswordEncoder(16).encode("123456"));
    }
    @Test
    public void test1() throws ParseException {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");

        Date date = null;
        String s = "2018/3/3";
        Object o = (Object)s;

        date = (o!=null && o.toString()!= ""? dateFormat1.parse(o.toString()): null);
        System.out.println(date);
    }

}
