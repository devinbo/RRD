package com.rrd.dao;

import com.rrd.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    User getUserWithNameAndPsd(User user);

    List<User> getinternal(User user);

    User getInternalById(@Param("id") String id);

    void delUser(@Param("ids") List<String> ids);

    void addUser(User user);

    void updateUser(User user);

    int getUserByLoginNmOrNo(User user);

    void tzAuth(@Param("id") String id, @Param("tzAuth") String tzAuth);
}
