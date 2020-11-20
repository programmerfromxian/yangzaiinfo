package com.yy.common;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yang
 * @date 2019/12/06 19:13
 */
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getRequestURL().toString().matches(".+.ico$")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String origin = request.getHeader("Origin");
            // 简单请求跨域，如果是跨域请求在响应头里面添加对应的Origin
            if (!StringUtils.isEmpty(origin)) {
                response.addHeader("Access-Control-Allow-Origin", origin);
            }
            // 非简单请求跨域
            response.addHeader("Access-Control-Allow-Headers", "content-type");
            // 允许跨域请求的方法
            response.addHeader("Access-Control-Allow-Methods", "*");
            // 预检命令缓存 1小时
            //        response.addHeader("Access-Control-Max-Age", "3600");
            // 携带cookie的跨域
            response.addHeader("Access-Control-Allow-Credentials", "true");
            // 放行方法
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
