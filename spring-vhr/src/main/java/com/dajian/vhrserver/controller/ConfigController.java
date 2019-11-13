package com.dajian.vhrserver.controller;

import com.dajian.vhrserver.pojo.Hr;
import com.dajian.vhrserver.pojo.Menu;
import com.dajian.vhrserver.service.MenuService;
import com.dajian.vhrserver.utils.HrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/sysmenu")
    public ResponseEntity<List<Menu>> sysmenu(){
        List<Menu> menus = menuService.getMenusByHrId();
        return ResponseEntity.ok(menus);
    }

    @RequestMapping("/hr")
    public Hr getCurrentUser(){
        return HrUtil.getCurrentHr();
    }
}
