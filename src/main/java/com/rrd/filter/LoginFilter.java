package com.rrd.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/4/14.
 */
public class LoginFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {
        if (req.getRequestURI().endsWith(".png")
                || req.getRequestURI().endsWith(".jpg")
                || req.getRequestURI().endsWith(".gif")
                || req.getRequestURI().endsWith(".jpeg")
                || req.getRequestURI().endsWith(".html")
                || req.getRequestURI().endsWith(".js")
                || req.getRequestURI().endsWith(".css")
                || req.getRequestURI().endsWith(".svg")
                || req.getRequestURI().endsWith(".eot")
                || req.getRequestURI().endsWith(".woff")
                || req.getRequestURI().endsWith(".ttf")
                || req.getRequestURI().endsWith(".jsp")
                || req.getRequestURI().endsWith("/freightToOut") //过滤运价接口
                || req.getRequestURI().contains("/:") //处理前端页面
                || req.getRequestURI().endsWith("/login") //登录页面
                || req.getRequestURI().endsWith("/reg")   //注册页面
                || req.getRequestURI().endsWith(".php")//不拦截.php请求
                || req.getRequestURI().endsWith(".pc")//不拦截pc客户端的接口请求
                ) {
            return true;
        } else {
            if (req.getSession().getAttribute("user") != null) {
                return true;
            } else {
                req.getRequestDispatcher("/admin/login.jsp").forward(req, res);
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
