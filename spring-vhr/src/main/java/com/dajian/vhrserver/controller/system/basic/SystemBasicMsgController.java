package com.dajian.vhrserver.controller.system.basic;

import com.dajian.vhrserver.pojo.Department;
import com.dajian.vhrserver.pojo.Menu;
import com.dajian.vhrserver.pojo.Role;
import com.dajian.vhrserver.service.DepartmentService;
import com.dajian.vhrserver.service.MenuService;
import com.dajian.vhrserver.service.RoleService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/basicMsg")
public class SystemBasicMsgController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.getAllRoles();
        if (CollectionUtils.isEmpty(roles)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/menuTree/{rid}")
    public ResponseEntity<Map<String, Object>> getSelMids(@PathVariable("rid") Long rid){
        Map<String, Object> map = new HashMap<>();
        List<Menu> menus = menuService.getMenuTreeData();

        List<Long> mids = menuService.getMenusByRid(rid);
        map.put("mids", mids);
        map.put("menus", menus);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/menuTree")
    public ResponseEntity<Map<String, Object>> getMenuTree(){
        Map<String, Object> map = new HashMap<>();
        List<Menu> menus = menuService.getMenuTreeData();

        map.put("menus", menus);
        return ResponseEntity.ok(map);
    }

    /**
     * 新增
     * @param role
     * @param mids
     * @return
     */
    @PostMapping("/role")
    public ResponseEntity<Void> addNewRole(Role role,@RequestParam("mids")List<Long> mids){
        roleService.addRole(role, mids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/role")
    public ResponseEntity<Void> updateRole(Role role,@RequestParam("mids")List<Long> mids){
        roleService.updateRole(role, mids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable("id") Long id){
        roleService.deleteRoleById(id);
        return ResponseEntity.ok().build();
    }


}
