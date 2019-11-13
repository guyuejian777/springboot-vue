package com.dajian.vhrserver.config;

import com.dajian.vhrserver.utils.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限拒绝处理
 */
@Component
public class VhrAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String,String> map = new HashMap<>();
        map.put("status","error");
        map.put("msg","权限不足，请联系管理员！");
        String s = JsonUtil.serializeToString(map);
        out.write(s);
        out.flush();
        out.close();

    }
}
