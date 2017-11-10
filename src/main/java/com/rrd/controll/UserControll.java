package com.rrd.controll;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.rrd.model.User;
import com.rrd.pjo.Result;
import com.rrd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/12.
 */
@RestController
@RequestMapping("user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserControll {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(User user) {
        return userService.login(user);
    }

    @PostMapping("getCurrUserInfo")
    public User getCurrUserInfo(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    @RequestMapping("loginout")
    public ModelAndView loginout(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/login");
        return modelAndView;
    }

    @PostMapping("getinternal")
    public PageInfo getinternal(PageInfo page, User user) {
        return userService.getinternal(page, user);
    }

    @PostMapping("getInternalById")
    public Result getInternalById (String id) {
        return userService.getInternalById(id);
    }

    @PostMapping("delUser")
    public Result delUser(String ids) {
        return userService.delUser(ids);
    }

    @PostMapping("andOrSaveUser")
    public Result andOrSaveUser(User user) {
        return userService.andOrSaveUser(user);
    }

    @PostMapping("tzAuth")
    public Result tzAuth(@RequestParam Map<String, String> params) {
        return userService.tzAuth(params);
    }
}
