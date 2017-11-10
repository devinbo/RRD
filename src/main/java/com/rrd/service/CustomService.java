package com.rrd.service;

import com.github.pagehelper.PageInfo;
import com.rrd.model.Custom;
import com.rrd.pjo.Result;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/12.
 */
public interface CustomService {
    Result getMenu();

    Result addOrUpdCustom(Custom custom);

    Result delCustom(String customIds);

    PageInfo getCustomList(PageInfo pageInfo, Map<String, String> param);

    Result getCustomById(String id);

    PageInfo getAuthList(PageInfo pageInfo, Map<String, Object> param);

    Result getAllCus(String key, String size);

    Result getAllOnlineCus(String key, String size);

    Result getAllCusWithKf(String key, String size);

    PageInfo getWorkAuthList(PageInfo pageInfo, Custom custom);

    Result passWorkAuth(String custom_id) throws Exception;

    Result refuseWorkAuth(String custom_id) throws Exception;

    PageInfo getLinkAuthList(PageInfo pageInfo, Custom custom);

    Result passLinkAuth(String custom_id) throws Exception;

    Result refuseLinkAuth(String custom_id) throws Exception;
}
