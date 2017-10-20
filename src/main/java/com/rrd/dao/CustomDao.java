package com.rrd.dao;

import com.rrd.model.Custom;
import com.rrd.pjo.Result;
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

    Result delCustom(@Param("ids") List<String> ids);

    List<Custom> getCustomList(@Param("param") Map<String, String> param);

    Custom getCustomById(@Param("id") String id);

    void addEduc(Custom custom);

    void addWork(Custom custom);

    void updateEduc(Custom custom);

    void updateWork(Custom custom);

    int getCustomWithPhone(@Param("phone") String phone, @Param("id") Long id);

    List<Map<String,Object>> getAuthList(@Param("param") Map<String, Object> param);
}
