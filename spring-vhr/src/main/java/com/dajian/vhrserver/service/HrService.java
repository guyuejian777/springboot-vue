package com.dajian.vhrserver.service;

import com.dajian.vhrserver.mapper.HrMapper;
import com.dajian.vhrserver.mapper.HrRoleMapper;
import com.dajian.vhrserver.pojo.Hr;
import com.dajian.vhrserver.pojo.PageResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HrService implements UserDetailsService {

    @Autowired
    private HrMapper hrMapper;
    @Autowired
    private HrRoleMapper hrRoleMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hr hr = hrMapper.getUserByUsername(s);
        if (hr == null) {
            throw new UsernameNotFoundException("用户名没对");
        }
        return hr;
    }

    public void hrRegistry(String username, String password) {
        //判断用户是否存在
        if (hrMapper.getUserByUsername(username) == null) {
            return;
        }
        String encode = new BCryptPasswordEncoder(16).encode(password);
        hrMapper.insertHr(username, encode);
    }

    /**
     * 根据条件查询分页结果集
     * @param key
     * @param page
     * @param rows
     * @return
     */
    public PageResult<Hr> queryHrsByPage(String key, Integer page, Integer rows) {
        int start = (page - 1) * rows;

        List<Hr> hrs = hrMapper.getHrsByPage(start, rows, key);
        PageInfo<Hr> pageInfo = new PageInfo<>(hrs);

        PageResult<Hr> result = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return result;
    }

    public void updateHrRoles(Long hrId, List<Long> rids) {
        hrRoleMapper.deleteHrRole(hrId);
        rids.forEach( rid -> {
            hrRoleMapper.saveHrRole(hrId, rid);
        });
    }

    public Hr getHrById(Long id) {
        return hrMapper.getHrById(id);
    }

    public void updateHrEnabled(Hr hr) {
        hrMapper.updateHrEnabled(hr);
    }

    public void deleteHrById(Long id) {
        hrMapper.deleteHrById(id);
    }
}
