package com.rrd.service;

import com.github.pagehelper.PageInfo;
import com.rrd.model.User;
import com.rrd.pjo.Result;

/**
 * Created by Administrator on 2017/10/12.
 */
public interface UserService {
    Result login(User user);

    PageInfo getinternal(PageInfo page, User user);

    Result getInternalById(String id);

    Result delUser(String ids);

    Result andOrSaveUser(User user);
}
