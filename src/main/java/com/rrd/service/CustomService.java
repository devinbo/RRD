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
}
