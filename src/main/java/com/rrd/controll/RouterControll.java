package com.rrd.controll;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/12.
 */
@Controller
public class RouterControll {


    //进入登录页
    @RequestMapping("/login")
    public String goLogin(HttpServletRequest request) {
        String index = (String) request.getServletContext().getAttribute("index");
//        System.out.print("登录时："+"views/login"+(StringUtils.isEmpty(index) ? "": "_"+index));
        return "views/login" + (StringUtils.isEmpty(index) ? "" : "_" + index);
    }

    //进入登录页
    @RequestMapping("/reg")
    public String goReg(HttpServletRequest request) {
        String index = (String) request.getServletContext().getAttribute("index");
        return "views/register" + (StringUtils.isEmpty(index) ? "" : "_" + index);
    }


    /**********后台路由**********/
    @RequestMapping("/admin/")
    public String goIndex() throws Exception {
        return "/admin/index";
    }

    //处理路由信息
    @RequestMapping("/admin/{id}")
    public String backIndex() {
        return "/admin/index";
    }

    //处理路由信息
    @RequestMapping("/admin/*/{id}")
    public String backMainIndex() {
        return "/admin/index";
    }

}
