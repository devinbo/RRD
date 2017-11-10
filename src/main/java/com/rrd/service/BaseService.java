package com.rrd.service;

import com.github.pagehelper.PageInfo;
import com.rrd.model.Product;
import com.rrd.pjo.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/18.
 */
public interface BaseService {
    List<Map<String,Object>> getDict(String dict_code);

    Map<String,Object> getRoles(Map<String, String> params);

    Result getMenues(Map<String, String> params);

    Result getAllMenues(Map<String, Object> params);

    Result delRoles(Map<String, Object> params);

    Result addRole(Map<String, Object> params);

    Result distributeRole(Map<String, Object> params);

    PageInfo productList(PageInfo pageInfo, String name);

    Result andOrUpdPruduct(Product product);

    Result getProductById(String id);

    Result getAllProduct();

    Result delProduct(String ids);
}
