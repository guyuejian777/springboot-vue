package com.dajian.vhrserver.config;

import com.dajian.vhrserver.filter.VhrFilterInvocationSecurityMetadataSource;
import com.dajian.vhrserver.service.HrService;
import com.dajian.vhrserver.utils.HrUtil;
import com.dajian.vhrserver.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * security配置
 */
@Configuration
public class VhrWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private HrService hrService;

    @Autowired
    private VhrFilterInvocationSecurityMetadataSource vhrFilterInvocationSecurityMetadataSource;

    @Autowired
    private VhrAccessDecisionManager vhrAccessDecisionManager;

    @Autowired
    private VhrAccessDeniedHandler vhrAccessDeniedHandler;


    //自定义身份验证管理器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService).passwordEncoder(new BCryptPasswordEncoder(16));
    }
    //创建和定制过滤器链
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html","/static/**","/login_p","/favicon.ico");
    }

    //请求匹配以进行调度和授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //注入后请求都会经过刚才的过滤器()，除了(configure(WebSecurity web)忽略的请求)
                        o.setSecurityMetadataSource(vhrFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(vhrAccessDecisionManager);
                        return o;
                    }//login_p是登录页，
                }).and().formLogin().loginPage("/login_p").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .permitAll().failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                Map<String,String > map = new HashMap<>();
                map.put("status","error");
                if ( exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException){
                    map.put("msg","用户名或密码输入错误，登录失败!");
                }else if (exception instanceof DisabledException){
                    map.put("msg","账户被禁用，登录失败，请联系管理员!");
                }else {
                    map.put("msg","登陆失败");
                }
                String s = JsonUtil.serializeToString(map);
                out.write(s);
                out.flush();
                out.close();
            }
        }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                Map<String ,Object> map = new HashMap<>();
                map.put("status","success");
                map.put("welcome","Welcome back! "+ HrUtil.getCurrentHr().getName());

                map.put("msg", HrUtil.getCurrentHr());
                String s = JsonUtil.serializeToString(map);
                out.write(s);
                out.flush();
                out.close();
            }
        }).and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(vhrAccessDeniedHandler);
    }
}
