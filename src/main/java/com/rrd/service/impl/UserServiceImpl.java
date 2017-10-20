package com.rrd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rrd.dao.UserDao;
import com.rrd.model.User;
import com.rrd.pjo.ResCode;
import com.rrd.pjo.Result;
import com.rrd.service.UserService;
import com.rrd.utils.PublicUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/12.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private HttpServletRequest request;

    @Override
    public Result login(User user) {
        String password = user.getPassword();
        if (StringUtils.isEmpty(user.getLoginname()) || StringUtils.isEmpty(password)) {
            return new Result(ResCode.Error.value(), "用户名或密码不能为空");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        User dbUser = userDao.getUserWithNameAndPsd(user);
        if(dbUser == null) {
            return new Result(ResCode.Error.value(), "用户名或密码错误");
        }
        request.getSession().setAttribute("user", dbUser);
        return new Result();
    }

    @Override
    public PageInfo getinternal(PageInfo page, User user) {
        PageHelper.startPage(page);
        List<User> userList = userDao.getinternal(user);
        return new PageInfo<>(userList);
    }

    @Override
    public Result getInternalById(String id) {
        return new Result(userDao.getInternalById(id));
    }

    @Override
    public Result delUser(String ids) {
        userDao.delUser(PublicUtil.toListByIds(ids));
        return new Result();
    }

    @Override
    public Result andOrSaveUser(User user) {
        User  currUser = getUser();
        int count = userDao.getUserByLoginNmOrNo(user);
        if(count > 0 ) {
            return new Result(ResCode.Error.value(), "员工编号或登录名重复");
        }
        if(user.getId() == null) {
            user.setCrtuser(currUser.getUsername());
            userDao.addUser(user);
        }else{
            user.setUpduser(currUser.getUsername());
            userDao.updateUser(user);
        }
        return new Result();
    }

    private User getUser() {
        return (User) request.getSession().getAttribute("user");
    }
}
