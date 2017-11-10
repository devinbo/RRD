package com.rrd.controll;

import com.github.pagehelper.PageInfo;
import com.rrd.model.Custom;
import com.rrd.model.Product;
import com.rrd.pjo.Result;
import com.rrd.plugin.JedisHelper;
import com.rrd.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/18.
 */
@RestController
@RequestMapping("base")
public class BaseControll {

    @Autowired
    private BaseService baseService;

    @Autowired
    private JedisHelper jedisHelper;

    @PostMapping("test")
    public Result test() throws Exception {
        System.out.println(jedisHelper.get("custom_1"));
        Custom custom = jedisHelper.getObj("custom_1", Custom.class);
        System.out.println(custom.getBirthday());
        return new Result();
    }

    @PostMapping("getdict")
    public List<Map<String, Object>> getDict(String dict_code) {
        return baseService.getDict(dict_code);
    }

    /*
    * 获取所有角色，已拥有的角色
    * */
    @RequestMapping("/getRoles")
    public @ResponseBody
    Result getRoles (@RequestParam Map<String, String> params, HttpServletRequest request) {
        Map<String,Object> roleMap = baseService.getRoles(params);
        return new Result(roleMap);
    }

    /*
 * 获取角色对应的菜单
 * */
    @RequestMapping("/getRoleMenues")
    public @ResponseBody Result getRoleMenues ( @RequestParam Map<String, String> params, HttpServletRequest request) {
        return baseService.getMenues(params);
    }
    /*
   * 获取所有菜单
   * */
    @RequestMapping("/getAllMenues")
    public @ResponseBody Result getAllMenues ( @RequestParam Map<String, Object> params, HttpServletRequest request) {
        return baseService.getAllMenues(params);
    }
    /*
   * 删除选中的角色
   * */
    @RequestMapping("/delRoles")
    public @ResponseBody Result delRoles ( @RequestParam Map<String, Object> params, HttpServletRequest request) {
        return baseService.delRoles(params);
    }
    /*添加角色
    * */
    @RequestMapping("/addRole")
    public @ResponseBody Result addRole(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return  baseService.addRole(params);
    }
    /*保存对账号的权限分配
    * */
    @RequestMapping("/distributeRole")
    public @ResponseBody Result distributeRole(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return  baseService.distributeRole(params);
    }

    @PostMapping("productList")
    public PageInfo productList(PageInfo pageInfo, String name) {
        return baseService.productList(pageInfo, name);
    }

    @RequestMapping("/andOrUpdPruduct")
    public Result andOrUpdPruduct(Product product) {
        return baseService.andOrUpdPruduct(product);
    }

    @PostMapping("getProductById")
    public Result getProductById(String id) {
        return baseService.getProductById(id);
    }

    @RequestMapping("getAllProduct")
    public Result getAllProduct() {
        return baseService.getAllProduct();
    }

    @RequestMapping("delProduct")
    public Result delProduct(String ids) {
        return baseService.delProduct(ids);
    }
}
