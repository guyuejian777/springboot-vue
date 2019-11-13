package com.dajian.vhrserver.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * 权限管理，权限不足时抛异常，有权限时直接return
 */
@Component
public class VhrAccessDecisionManager implements AccessDecisionManager {
    /**
     *
     * @param authentication 保存了当前登录用户的角色信息
     * @param o
     * @param attributes    UrlFilterInvocationSecurityMetadataSource的getAttributes方法传来的角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> attributes) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = attributes.iterator();
        while (iterator.hasNext()){
            ConfigAttribute configAttribute = iterator.next();
            //当前请求需要的角色
            String needRole = configAttribute.getAttribute();
            //如果当前请求需要的权限是ROLE_LOGIN，则表示登录即可访问，和角色没关系
            if ("ROLE_LOGIN".equals(needRole)){
                //如果是AnonymousAuthenticationToken的实例，则表示没有登录，没有
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new BadCredentialsException("未登录");
                    //如果登录了直接return，则这个请求成功执行。
                }else {
                    return;
                }
            }
            //获取当前用户的权限
            //如果当前用户具有角色A,B, 当前请求需要的角色是B,C ，那么当前用户只有具有B或C就授权成功
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            //遍历查看是否是需要的权限，如果具备就直接返回
            for (GrantedAuthority authority: authorities){
                if (authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
