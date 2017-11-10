package com.rrd.dao;

import com.rrd.model.Custom;
import com.rrd.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CustomDao {

    List<Map<String,Object>> getRuleTask(@Param("roleArr") String[] roleArr);

    List<Map<String,Object>> getTaskWithIds(@Param("taskIds") Set<String> taskIds);

    List<Map<String,Object>> queryParentTask(@Param("Ids") Set<String> parentIds);

    void addCustom(Custom custom);

    void updateCustom(Custom custom);

    void delCustom(@Param("ids") List<String> ids);

    List<Custom> getCustomList(@Param("param") Map<String, String> param, @Param("user") User user);

    Custom getCustomById(@Param("id") String id);

    void addEduc(Custom custom);

    void addWork(Custom custom);

    void updateEduc(Custom custom);

    void updateWork(Custom custom);

    int getCustomWithPhone(@Param("phone") String phone, @Param("id") Long id);

    List<Map<String,Object>> getAuthList(@Param("param") Map<String, Object> param);

    List<Custom> getAllCus(@Param("key") String key, @Param("size") Integer size);

    List<Custom> getAllOnlineCus(@Param("key") String key, @Param("size") Integer size);

    List<User> getAllCusWithKf(@Param("key") String key, @Param("size") Integer size);

    List<Custom> getWorkAuthList(Custom custom);

    void passWorkAuth(@Param("custom_id") String custom_id);

    void refuseWorkAuth(@Param("custom_id") String custom_id);

    List<Custom> getLinkAuthList(Custom custom);

    void passLinkAuth(@Param("custom_id") String custom_id);

    void refuseLinkAuth(@Param("custom_id") String custom_id);

    void addStudent(Custom custom);

    void updateStudent(Custom custom);
}
