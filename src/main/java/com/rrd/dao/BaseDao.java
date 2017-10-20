package com.rrd.dao;

import com.rrd.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/18.
 */
public interface BaseDao {
    List<Map<String,Object>> getDict(@Param("dict_code") String dict_code);

    List<Map<String,Object>> getRoles(@Param("role_id") String role_id);//获取角色

    Map<String, Object> getSelfRoleId(@Param("login_nm") String login_nm);//获取自己的角色ID

    String getMenues(@Param("rolenm") String rolenm,@Param("roleid")String roleid);

    Map<String,Object> getTasks (@Param("taskid") String taskid);//获取菜单

    List<Map<String,Object>> getAllTask();

    void delRoles(@Param("rolenm")String rolenm);

    int getRole(@Param("param")Map<String,Object> param);

    void updRole(@Param("param") Map<String,Object> map);

    void updRoleRel(@Param("param") Map<String,Object> map);

    void addRoleRel(@Param("param") Map<String,Object> map);

    void addRole(@Param("param") Map<String,Object> map);

    String getRoleId(@Param("rolenm") String rolenm);

    void distributeRole(@Param("roleids")String roleIdstr,@Param("userno")String userno);

    List<Product> getproductList(@Param("name") String name);

    void addProduct(Product product);

    void updProduct(Product product);

    Product getProductById(String id);
}
