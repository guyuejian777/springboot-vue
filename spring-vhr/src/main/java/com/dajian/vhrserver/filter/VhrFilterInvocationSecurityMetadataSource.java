package com.dajian.vhrserver.filter;

import com.dajian.vhrserver.pojo.Menu;
import com.dajian.vhrserver.pojo.Role;
import com.dajian.vhrserver.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 获取当前请求地址，并获取该地址需要的用户角色
 */
@Component
public class VhrFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 如果返回null，那么当前这个请求不需要任何角色就能访问，甚至不需要登录
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //如果地址是login_p，是登录页，直接返回null，
//        if ("/login_p".equals(requestUrl)){
//            return null;
//        }
        List<Menu> menus = menuService.getAllMenu();
        for (Menu menu : menus){
            //请求地址是否在设置的过滤请求路径里，并且该路径存在至少一个角色
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size()>0 ){
                List<Role> roles = menu.getRoles();

                String[] values = new String[roles.size()];
                for (int i=0; i<roles.size(); i++){
                    values[i] = roles.get(i).getName();
                }
                //返回角色集合
                return SecurityConfig.createList(values);
            }
        }
        //所有未匹配到的路径，都是认证（登录）后可访问，返回一个ROLE_LOGIN的角色，接下来会到UrlAccessDecisionManager类
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
