package com.dajian.vhrserver.service;

import com.dajian.vhrserver.mapper.MenuMapper;
import com.dajian.vhrserver.pojo.Menu;
import com.dajian.vhrserver.utils.HrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"menus_cache"})
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> getAllMenu() {
        List<Menu> menus = menuMapper.getAllMenu();
        return menus;
    }

    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(HrUtil.getCurrentHr().getId());

    }

    public List<Menu> getMenuTreeData() {
        return menuMapper.getMenuTreeData();
    }

    public List<Long> getMenusByRid(Long rid) {
        return menuMapper.getMenusByRid(rid);
    }
}
