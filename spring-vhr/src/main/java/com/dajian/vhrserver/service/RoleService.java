package com.dajian.vhrserver.service;

import com.dajian.vhrserver.mapper.MenuRoleMapper;
import com.dajian.vhrserver.mapper.RoleMapper;
import com.dajian.vhrserver.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;


    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    /**
     *
     * @param role
     * @param mids
     */
    @Transactional
    public void addRole(Role role, List<Long> mids) {
        if (!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_" + role.getName());
        }
        //1.新建角色
        role.setId(null);
        roleMapper.addRole(role);
        //根据nameZh查询role的id
        Role result = roleMapper.getRoleByNameZh(role.getNameZh());

        //新建menu_role中间表
        mids.forEach( mid -> {
            menuRoleMapper.addRole2Menu(mid, result.getId());
        });
    }

    @Transactional
    public void updateRole(Role role, List<Long> mids) {
        System.out.println(role.getId());
        System.out.println(role.getName());
        System.out.println(role.getNameZh());
        menuRoleMapper.deleteByRid(role.getId());
        roleMapper.updateRole(role);

        //新建menu_role中间表
        mids.forEach( mid -> {
            menuRoleMapper.addRole2Menu(mid, role.getId());
        });
    }

    public void deleteRoleById(Long id) {
        menuRoleMapper.deleteByRid(id);
        roleMapper.deleteById(id);
    }
}
