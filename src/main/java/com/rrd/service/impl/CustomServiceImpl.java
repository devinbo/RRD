package com.rrd.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rrd.dao.CustomDao;
import com.rrd.model.Custom;
import com.rrd.model.User;
import com.rrd.pjo.ResCode;
import com.rrd.pjo.Result;
import com.rrd.plugin.JedisHelper;
import com.rrd.service.CustomService;
import com.rrd.utils.PublicUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/10/12.
 */
@Service
public class CustomServiceImpl implements CustomService {

    @Autowired
    private JedisHelper jedisHelper;

    @Resource
    private HttpServletRequest request;

    @Resource
    private CustomDao customDao;

    @Override
    public Result getMenu() {
        User user = getUser();
        String roleids = user.getM_role_id();
        System.out.println(roleids);
        if (StringUtils.isEmpty(roleids)) {
            return new Result();
        }
        String[] roleArr = roleids.split(",");
        List<Map<String, Object>> rules = customDao.getRuleTask(roleArr);
        Set<String> taskIds = new HashSet<String>();
        for (Map<String, Object> map : rules) {
            if (map.get("m_task_id") == null || "".equals(map.get("m_task_id"))) {
                continue;
            }
            String task_id = String.valueOf(map.get("m_task_id"));
            String[] task_Ids = task_id.split(",");
            for (String tk : task_Ids) {
                taskIds.add(tk);
            }
        }
        //获取所有任务
        List<Map<String, Object>> taskList = customDao.getTaskWithIds(taskIds);
        //循环任务，封装结果集
        return dealTaskResult(taskList);
    }


    private Result dealTaskResult(List<Map<String, Object>> taskList) {
        Map<String, Map<String, Object>> result = new LinkedHashMap<String, Map<String, Object>>();
        Set<String> parentIds = new HashSet<String>();
        for (Map<String, Object> map : taskList) {
            //获取所有的父节点
            if (map.get("parent_id") != null && !"".equals(map.get("parent_id"))) {
                parentIds.add(map.get("parent_id").toString());
            }
        }
        //查询父节点信息
        List<Map<String, Object>> parentTasks = customDao.queryParentTask(parentIds);
        for (Map<String, Object> map : parentTasks) {
            //新生成一个map
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("id", map.get("m_task_id"));
            dataMap.put("name", map.get("task_name"));
            dataMap.put("submenue", new ArrayList());
            dataMap.put("parenturl", map.get("location"));
            result.put(map.get("m_task_id").toString(), dataMap);
        }
        //第二次循环， 处理二级目录
        for (Map<String, Object> map : taskList) {
            if (map.get("parent_id") != null && !"".equals(map.get("parent_id"))) {
                Map<String, Object> subData = result.get(map.get("parent_id").toString());
                List<Map<String, Object>> list = (List<Map<String, Object>>) subData.get("submenue");
                Map<String, Object> child = new HashMap<String, Object>();
                child.put("subname", map.get("task_name"));
                child.put("subicon", map.get("icon_type"));
                child.put("suburl", map.get("location"));
                child.put("subid", map.get("m_task_id"));
                list.add(child);
            }
        }
        return new Result(result.values());
    }

    /**
     * 添加或修改客户信息
     *
     * @param custom
     * @return
     */
    @Override
    public Result addOrUpdCustom(Custom custom) {
        int count = customDao.getCustomWithPhone(custom.getPhone(), custom.getCustom_id());
        if (count > 0) {
            return new Result(ResCode.Error.value(), "该手机号已存在");
        }
        if (custom.getCustom_id() == null) {
            custom.setCrtuser(getUser().getUsername());
            if(!StringUtils.isEmpty(custom.getPurpose()) && Integer.valueOf(custom.getPurpose()) >= 4) {
                //表示高等教育， 添加学历信息
                //添加学历信息
                customDao.addEduc(custom);
            }
            //添加工作信息
            customDao.addWork(custom);
            //添加基本信息
            customDao.addCustom(custom);
            //表示在校生，添加在校生信息
            if("1".equals(custom.getPurpose())) {
                customDao.addStudent(custom);
            }
        } else {
            custom.setUpduser(getUser().getUsername());
            custom.setCrtuser(getUser().getUsername());
            if (custom.getPurpose() != null && Integer.valueOf(custom.getPurpose()) >= 4) {
                //更新教育信息
                if(custom.getEduc_id() != null) {
                    customDao.updateEduc(custom);
                }else{
                    customDao.addEduc(custom);
                }
            }else if("1".equals(custom.getPurpose())) {
                if(custom.getStudent() != null) {
                    //更新在校生信息
                    customDao.updateStudent(custom);
                }else{
                    customDao.addStudent(custom);
                }
            }
            if (custom.getWork_id() != null) {
                //更新工作信息
                customDao.updateWork(custom);
            } else {
                customDao.addWork(custom);
            }
            customDao.updateCustom(custom);
        }

        //如果是线上客户，那么添加到redis中去
        if("1".equals(custom.getRecsts())) {
            //客户信息添加到redis中
            //去除图片信息。以防信息过大，
            custom.setPicture(null);
            jedisHelper.setObjWithDay("custom_"+custom.getCustom_id(), custom, 3);
        }
        return new Result();
    }

    @Override
    public Result delCustom(String customIds) {
        customDao.delCustom(PublicUtil.toListByIds(customIds));
        //请求redis中客户信息
        for (String id : PublicUtil.toListByIds(customIds)) {
            jedisHelper.deleteByKey("custom_"+id);
        }
        //请求redis中对应的用户
        return new Result();
    }

    @Override
    public PageInfo getCustomList(PageInfo pageInfo, Map<String, String> param) {
        PageHelper.startPage(pageInfo);
        if (param == null || !StringUtils.isEmpty(param.get("crtdatestr"))) {
            param.put("startdate", param.get("crtdatestr").split(",")[0]);
            param.put("enddate", param.get("crtdatestr").split(",")[1]);
        }
        List<Custom> list = customDao.getCustomList(param, getUser());
        return new PageInfo<>(list);
    }

    @Override
    public Result getCustomById(String id) {
        Custom custom = customDao.getCustomById(id);
        return new Result(custom);
    }

    @Override
    public PageInfo getAuthList(PageInfo pageInfo, Map<String, Object> param) {
        PageHelper.startPage(pageInfo);
        List<Map<String, Object>>  list = customDao.getAuthList(param);
        return new PageInfo<>(list);
    }

    @Override
    public Result getAllCus(String key, String size) {
        List<Custom> list = customDao.getAllCus(key, Integer.valueOf(size));
        return new Result(list);
    }

    @Override
    public Result getAllOnlineCus(String key, String size) {
        List<Custom> list = customDao.getAllOnlineCus(key, Integer.valueOf(size));
        return new Result(list);
    }

    @Override
    public Result getAllCusWithKf(String key, String size) {
        List<User> list = customDao.getAllCusWithKf(key, Integer.valueOf(size));
        return new Result(list);
    }

    @Override
    public PageInfo getWorkAuthList(PageInfo pageInfo, Custom custom) {
        PageHelper.startPage(pageInfo);
        List<Custom> list = customDao.getWorkAuthList(custom);
        return new PageInfo<>(list);
    }

    @Override
    public Result passWorkAuth(String custom_id) throws Exception {
        customDao.passWorkAuth(custom_id);
        Custom custom = customDao.getCustomById(custom_id);
        jedisHelper.setWithSec(custom_id, JSON.toJSONString(custom));
        return new Result();
    }

    @Override
    public Result refuseWorkAuth(String custom_id) throws Exception {
        customDao.refuseWorkAuth(custom_id);
        Custom custom = customDao.getCustomById(custom_id);
        jedisHelper.setWithSec(custom_id, JSON.toJSONString(custom));
        return new Result();
    }

    @Override
    public PageInfo getLinkAuthList(PageInfo pageInfo, Custom custom) {
        PageHelper.startPage(pageInfo);
        List<Custom> list = customDao.getLinkAuthList(custom);
        return new PageInfo<>(list);
    }

    @Override
    public Result passLinkAuth(String custom_id) throws Exception {
        customDao.passLinkAuth(custom_id);
        Custom custom = customDao.getCustomById(custom_id);
        jedisHelper.setWithSec(custom_id, JSON.toJSONString(custom));
        return new Result();
    }

    @Override
    public Result refuseLinkAuth(String custom_id) throws Exception {
        customDao.refuseLinkAuth(custom_id);
        Custom custom = customDao.getCustomById(custom_id);
        jedisHelper.setWithSec(custom_id, JSON.toJSONString(custom));
        return new Result();
    }

    /**
     * 获取登录的用户
     * @return
     */
    private User getUser() {
        return (User) request.getSession().getAttribute("user");
    }
}
