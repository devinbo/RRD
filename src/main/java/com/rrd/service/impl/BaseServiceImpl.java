package com.rrd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rrd.dao.BaseDao;
import com.rrd.model.Product;
import com.rrd.model.User;
import com.rrd.pjo.ResCode;
import com.rrd.pjo.Result;
import com.rrd.service.BaseService;
import com.rrd.utils.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<Map<String, Object>> getDict(String dict_code) {
        return baseDao.getDict(dict_code);
    }

    @Override
    public Map<String, Object> getRoles(Map<String, String> params) {
        String login_nm = params.get("usernm");//准备分配权限的账号
        List<Map<String, Object>> allrolesList = new ArrayList<>();
        List<String> showList = new ArrayList<>();
        List<String> selfrolesList = new ArrayList<>();
        allrolesList = baseDao.getRoles("");//所有角色
        for (int j = 0; j < allrolesList.size(); j++) {
            showList.add(allrolesList.get(j).get("rolenm").toString());
        }
        Map<String, Object> rseRfoleidMap = baseDao.getSelfRoleId(login_nm);//获取自己角色的id
        if (rseRfoleidMap != null) {
            String m_role_id = (String) rseRfoleidMap.get("m_role_id");
            if (m_role_id != null && m_role_id.length() > 0) {
                String[] roleidArr = m_role_id.split(",");
                for (int i = 0; i < roleidArr.length; i++) {
                    for (Map<String, Object> map : baseDao.getRoles(roleidArr[i])) {
                        selfrolesList.add(map.get("rolenm").toString());
                    }
                }
            } else {
                selfrolesList = null;
            }
        } else {
            selfrolesList = null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("allrole", allrolesList);
        map.put("selfrole", selfrolesList);
        map.put("showList", showList);
        return map;
    }

    @Override
    public Result getMenues(Map<String, String> params) {
        String roles = params.get("roles");
        String[] roleArr = roles.split(",");
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> tasksMap = new HashMap<>();
        String m_task_ids = "";
        String[] mtaskidArr = null;
        String info = "";//提示信息
        for (int i = 0; i < roleArr.length; i++) {
            m_task_ids = baseDao.getMenues(roleArr[i], null);
            if (m_task_ids != null) {
                mtaskidArr = m_task_ids.split(",");
                for (int j = 0; j < mtaskidArr.length; j++) {
                    tasksMap = baseDao.getTasks(mtaskidArr[j]);
                    Map<String, Object> menueMap = new HashMap<>();//一级菜单
                    List<Map<String, String>> subMenulist = new ArrayList<>();//二级菜单集合
                    if (tasksMap.get("parent_id") == null || tasksMap.get("parent_id").toString().length() == 0) {//一级菜单
                        if (resultList.size() > 0) {//不是第一条
                            for (int k = 0; k < resultList.size(); k++) {
                                String list_id = resultList.get(k).get("id").toString();
                                String tskmap_id = tasksMap.get("m_task_id").toString();
                                if (!list_id.equals(tskmap_id)) {
//                                if(resultList.get(k).get("id").toString()!=tasksMap.get("m_task_id").toString()){
                                    if (k == resultList.size() - 1) {//循环到最后依然没有此父菜单
                                        menueMap.put("id", tasksMap.get("m_task_id"));
                                        menueMap.put("name", tasksMap.get("task_name"));
                                        menueMap.put("submenue", subMenulist);
                                        resultList.add(menueMap);
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        } else {//第一条
                            menueMap.put("id", tasksMap.get("m_task_id"));
                            menueMap.put("name", tasksMap.get("task_name"));
                            menueMap.put("submenue", subMenulist);
                            resultList.add(menueMap);
                        }
                    } else {//二级菜单
                        if (resultList.size() != 0) {
                            for (int m = 0; m < resultList.size(); m++) {
                                if (resultList.get(m).get("id") != null) {
                                    if (resultList.get(m).get("id").toString().equals(tasksMap.get("parent_id").toString())) {//有此父级菜单

                                        if (resultList.get(m).get("submenue") != null && ((List<String>) resultList.get(m).get("submenue")).size() > 0) {//已经有了子菜单集合
                                            for (int n = 0; n < ((List<Map<String, String>>) resultList.get(m).get("submenue")).size(); n++) {
                                                if (((List<Map<String, String>>) resultList.get(m).get("submenue")).get(n).get("subname").equals(tasksMap.get("task_name").toString())) {//已经存在此二级菜单
                                                    break;
                                                } else {
                                                    if (n == ((List<Map<String, String>>) resultList.get(m).get("submenue")).size() - 1) {
                                                        Map<String, String> map = new HashMap<>();
                                                        map.put("subname", tasksMap.get("task_name").toString());
                                                        map.put("subid", tasksMap.get("m_task_id").toString());
                                                        map.put("parent_nm", tasksMap.get("parent_nm").toString());
                                                        map.put("parent_id", tasksMap.get("parent_id").toString());
                                                        ((List<Map<String, String>>) resultList.get(m).get("submenue")).add(map);
                                                        break;
                                                    }
                                                }
                                            }
                                        } else {
                                            Map<String, String> map = new HashMap<>();
                                            map.put("subname", tasksMap.get("task_name").toString());
                                            map.put("subid", tasksMap.get("m_task_id").toString());
                                            map.put("parent_nm", tasksMap.get("parent_nm").toString());
                                            map.put("parent_id", tasksMap.get("parent_id").toString());
                                            ((List<Map<String, String>>) resultList.get(m).get("submenue")).add(map);
                                        }
                                        break;
                                    } else {//集合中无此二级菜单的父菜单
                                        if (m == resultList.size() - 1) {
                                            Map<String, String> submap = new HashMap<>();
                                            submap.put("subname", tasksMap.get("task_name").toString());
                                            submap.put("subid", tasksMap.get("m_task_id").toString());
                                            subMenulist.add(submap);
                                            menueMap.put("id", tasksMap.get("parent_id"));
                                            menueMap.put("name", tasksMap.get("parent_nm"));
                                            menueMap.put("submenue", subMenulist);
                                            resultList.add(menueMap);
                                        }
                                    }
                                } else {
                                    if (m == resultList.size() - 1) {
                                        Map<String, String> submap = new HashMap<>();
                                        submap.put("subname", tasksMap.get("task_name").toString());
                                        submap.put("subid", tasksMap.get("m_task_id").toString());
                                        submap.put("parent_nm", tasksMap.get("parent_nm").toString());
                                        submap.put("parent_id", tasksMap.get("parent_id").toString());
                                        subMenulist.add(submap);
                                        menueMap.put("submenue", subMenulist);
                                        menueMap.put("id", tasksMap.get("parent_id"));
                                        menueMap.put("name", tasksMap.get("parent_nm"));
                                        resultList.add(menueMap);
                                    }
                                }
                            }
                        } else {
                            Map<String, String> submap = new HashMap<>();
                            submap.put("subname", tasksMap.get("task_name").toString());
                            submap.put("subid", tasksMap.get("m_task_id").toString());
                            subMenulist.add(submap);
                            menueMap.put("submenue", subMenulist);
                            menueMap.put("id", tasksMap.get("parent_id"));
                            menueMap.put("name", tasksMap.get("parent_nm"));
                            resultList.add(menueMap);
                        }
                    }
                }
            }
        }
        return new Result(ResCode.SUCCESS.value(), info, resultList);
    }

    @Override
    public Result getAllMenues(Map<String, Object> params) {
        String selectedrole = params.get("rolenm") == null ? "" : params.get("rolenm").toString();//选中的角色名
        String role_tasks = "";
        String[] role_task_ids = new String[]{};
        if (selectedrole != null && selectedrole.length() > 0) {
            role_tasks = baseDao.getMenues(selectedrole, null);//角色对应的id
            role_task_ids = role_tasks.split(",");
        }
       /* for(int i =0;i<role_task_ids.length;i++){
            System.out.println("元素是："+role_task_ids[i]);
        }*/
        List<Map<String, Object>> resultList = new ArrayList<>();
        String info = "";//提示信息
        List<Map<String, Object>> alltaskList = baseDao.getAllTask();//获取到所有task
        for (Map<String, Object> tasksMap : alltaskList) {
            Map<String, Object> menueMap = new HashMap<>();//一级菜单
            List<Map<String, String>> subMenulist = new ArrayList<>();//二级菜单集合
            if (tasksMap.get("parent_id") == null || tasksMap.get("parent_id").toString().length() == 0) {//一级菜单
                if (resultList.size() > 0) {//不是第一条
                    for (int k = 0; k < resultList.size(); k++) {
                        if (resultList.get(k).get("id") == null || resultList.get(k).get("id").toString().length() == 0 || (!resultList.get(k).get("id").equals(tasksMap.get("parent_id")))) {
                            if (k == resultList.size() - 1) {//循环到最后依然没有此父菜单
                                menueMap.put("id", tasksMap.get("m_task_id"));
                                menueMap.put("name", tasksMap.get("task_name"));
                                menueMap.put("submenue", subMenulist);
                                resultList.add(menueMap);
                                break;
                            } else {
                                continue;
                            }
                        } else {
                            break;
                        }
                    }
                } else {//第一条
                    menueMap.put("id", tasksMap.get("m_task_id"));
                    menueMap.put("name", tasksMap.get("task_name"));
                    menueMap.put("submenue", subMenulist);
                    resultList.add(menueMap);
                }
            } else {//二级菜单
                if (resultList.size() != 0) {
                    for (int m = 0; m < resultList.size(); m++) {
                        if (resultList.get(m).get("id") != null) {
                            if (resultList.get(m).get("id").toString().equals(tasksMap.get("parent_id").toString())) {//有此父级菜单
                                if (resultList.get(m).get("submenue") != null && ((List<String>) resultList.get(m).get("submenue")).size() > 0) {//已经有了子菜单集合
                                    for (int n = 0; n < ((List<Map<String, String>>) resultList.get(m).get("submenue")).size(); n++) {
                                        if (((List<Map<String, String>>) resultList.get(m).get("submenue")).get(n).get("subname").equals(tasksMap.get("task_name").toString())) {//已经存在此二级菜单
                                            break;
                                        } else {
                                            if (n == ((List<Map<String, String>>) resultList.get(m).get("submenue")).size() - 1) {
                                                Map<String, String> map = new HashMap<>();
                                                map.put("subname", tasksMap.get("task_name").toString());
                                                map.put("subid", tasksMap.get("m_task_id").toString());
                                                map.put("parent_nm", tasksMap.get("parent_nm").toString());
                                                map.put("parent_id", tasksMap.get("parent_id").toString());
                                                map.put("choosed", "0");
                                                if (role_task_ids != null && Arrays.asList(role_task_ids).contains(tasksMap.get("m_task_id").toString())) {
                                                    map.put("choosed", "1");
                                                    resultList.get(m).put("choosed", "1");
                                                }
                                                ((List<Map<String, String>>) resultList.get(m).get("submenue")).add(map);
                                            }
                                        }
                                    }
                                } else {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("subname", tasksMap.get("task_name").toString());
                                    map.put("subid", tasksMap.get("m_task_id").toString());
                                    map.put("parent_nm", tasksMap.get("parent_nm").toString());
                                    map.put("parent_id", tasksMap.get("parent_id").toString());
                                    map.put("choosed", "0");
                                    if (role_task_ids != null && Arrays.asList(role_task_ids).contains(tasksMap.get("m_task_id").toString())) {

                                        map.put("choosed", "1");
                                        resultList.get(m).put("choosed", "1");
                                    }
                                    ((List<Map<String, String>>) resultList.get(m).get("submenue")).add(map);
                                }
                                break;
                            } else {//集合中无此二级菜单的父菜单
                                if (m == resultList.size() - 1) {
                                    Map<String, String> submap = new HashMap<>();
                                    submap.put("subname", tasksMap.get("task_name").toString());
                                    submap.put("subid", tasksMap.get("m_task_id").toString());
                                    submap.put("parent_nm", tasksMap.get("parent_nm").toString());
                                    submap.put("parent_id", tasksMap.get("parent_id").toString());
                                    submap.put("choosed", "0");
                                    if (role_task_ids != null && Arrays.asList(role_task_ids).contains(tasksMap.get("m_task_id").toString())) {
                                        submap.put("choosed", "1");
                                        menueMap.put("choosed", "1");
                                    }
                                    subMenulist.add(submap);
                                    menueMap.put("id", tasksMap.get("parent_id"));
                                    menueMap.put("name", tasksMap.get("parent_nm"));
                                    menueMap.put("submenue", subMenulist);
                                    resultList.add(menueMap);
                                }
                            }
                        } else {
                            if (m == resultList.size() - 1) {
                                Map<String, String> submap = new HashMap<>();
                                submap.put("subname", tasksMap.get("task_name").toString());
                                submap.put("subid", tasksMap.get("m_task_id").toString());
                                submap.put("parent_nm", tasksMap.get("parent_nm").toString());
                                submap.put("parent_id", tasksMap.get("parent_id").toString());
                                submap.put("choosed", "0");
                                if (role_task_ids != null && Arrays.asList(role_task_ids).contains(tasksMap.get("m_task_id").toString())) {
                                    submap.put("choosed", "1");
                                    menueMap.put("choosed", "1");
                                }
                                subMenulist.add(submap);
                                menueMap.put("submenue", subMenulist);
                                menueMap.put("id", tasksMap.get("parent_id"));
                                menueMap.put("name", tasksMap.get("parent_nm"));
                                resultList.add(menueMap);
                            }
                        }
                    }
                } else {
                    Map<String, String> submap = new HashMap<>();
                    submap.put("subname", tasksMap.get("task_name").toString());
                    submap.put("subid", tasksMap.get("m_task_id").toString());
                    submap.put("parent_nm", tasksMap.get("parent_nm").toString());
                    submap.put("parent_id", tasksMap.get("parent_id").toString());
                    submap.put("choosed", "0");
                    if (role_task_ids != null && Arrays.asList(role_task_ids).contains(tasksMap.get("m_task_id").toString())) {
                        submap.put("choosed", "1");
                        menueMap.put("choosed", "1");
                    }
                    subMenulist.add(submap);
                    menueMap.put("submenue", subMenulist);
                    menueMap.put("id", tasksMap.get("parent_id"));
                    menueMap.put("name", tasksMap.get("parent_nm"));
                    resultList.add(menueMap);
                }
            }
        }
        return new Result(ResCode.SUCCESS.value(), info, resultList);
    }

    @Override
    public Result delRoles(Map<String, Object> params) {
        String[] roleArr = params.get("roles").toString().split(",");
        for (int i = 0; i < roleArr.length; i++) {
            baseDao.delRoles(roleArr[i].toString());
        }
        return new Result(ResCode.SUCCESS.value());
    }

    @Override
    public Result addRole(Map<String, Object> params) {
        Result result = new Result();
        String nowtime = PublicUtil.getDateTime();
        params.put("time", nowtime);
        int i = baseDao.getRole(params);
        if (i > 0) {
            result.setCode(ResCode.Error.value());
            result.setMessage("角色名已经存在");
        } else {
            if (params.get("roleid") != null && params.get("roleid").toString().length() > 0) {//修改
                baseDao.updRole(params);
                baseDao.updRoleRel(params);
                result.setMessage("修改成功");
            } else {//新添加
                baseDao.addRole(params);
                baseDao.addRoleRel(params);
                result.setMessage("添加成功");
            }
        }
        return result;
    }

    @Override
    public Result distributeRole(Map<String, Object> params) {
        String rolenames = params.get("rolenames").toString();
        String[] roleArr = rolenames.split(",");
        String roleids = "";
        String user = params.get("user").toString();
        for (int i = 0; i < roleArr.length; i++) {
            roleids = roleids + "," + baseDao.getRoleId(roleArr[i]);
        }
        roleids = roleids.substring(1);
        baseDao.distributeRole(roleids, user);
        return new Result();
    }

    @Override
    public PageInfo productList(PageInfo pageInfo, String name) {
        PageHelper.startPage(pageInfo);
        List<Product> list = baseDao.getproductList(name);
        return new PageInfo<>(list);
    }

    @Override
    public Result andOrUpdPruduct(Product product) {
        //查询产品名称是否重复
        int num = baseDao.hasProdUctWithName(product.getId(), product.getName());
        if(num > 0 ) {
            return new Result(ResCode.Error.value(), "产品名称已存在");
        }
        if (product.getId() == null) {
            product.setCrtuser(getUser().getUsername());
            //设置产品每期价格
            setProductDeadFee(product);
            baseDao.addProduct(product);
        } else {
            product.setUpduser(getUser().getUsername());
            //设置产品每期价格
            setProductDeadFee(product);
            baseDao.updProduct(product);
        }
        return new Result();
    }

    @Override
    public Result getProductById(String id) {
        Product product = baseDao.getProductById(id);
        return new Result(product);
    }

    @Override
    public Result getAllProduct() {
        List<Product> list = baseDao.getAllProduct();
        return new Result(list);
    }

    @Override
    public Result delProduct(String ids) {
        baseDao.delProduct(PublicUtil.toListByIds(ids));
        return new Result();
    }

    /**
     * 获取登录的用户
     * @return
     */
    private User getUser() {
        return (User) request.getSession().getAttribute("user");
    }


    public void setProductDeadFee(Product product) {
        //总费用
        BigDecimal price = product.getPrice();
        //期限
        Integer deadLine = product.getDeadline();
        BigDecimal deadPirce = price.divide(new BigDecimal(deadLine), 2, BigDecimal.ROUND_CEILING);
        product.setDeadprice(deadPirce);
    }
}
