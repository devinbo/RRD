package com.rrd.controll;

import com.github.pagehelper.PageInfo;
import com.rrd.model.Custom;
import com.rrd.pjo.Result;
import com.rrd.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * Created by Administrator on 2017/10/12.
 */
@RestController
@RequestMapping("/custom")
public class CustomControll {

    @Autowired
    private CustomService customService;

    /**
     * 获取所有的客户信息
     * @param key
     * @return
     */
    @RequestMapping("getAllCus")
    public Result getAllCus(String key, String size) {
        return customService.getAllCus(key, size);
    }
    //获取所有的线上客户
    @RequestMapping("getAllOnlineCus")
    public Result getAllOnlineCus(String key, String size) {
        return customService.getAllOnlineCus(key, size);
    }

    /**
     * 加载个人菜单
     * @return
     */
    @RequestMapping("/getMenu")
    public Result getMenu() {
        return customService.getMenu();
    }

    @RequestMapping("/addOrUpdCustom")
    public Result addOrUpdCustom(Custom custom) {
        return customService.addOrUpdCustom(custom);
    }

    @RequestMapping("/delCustom")
    public Result delCustom(String ids) {
        return customService.delCustom(ids);
    }

    @RequestMapping("/getcustomlist")
    public PageInfo getCustomList(PageInfo pageInfo, @RequestParam Map<String, String> param) {
        return customService.getCustomList(pageInfo, param);
    }

    @RequestMapping("/getCustomById")
    public Result getCustomById(String id) {
        return customService.getCustomById(id);
    }

    @RequestMapping("/getAuthList")
    public PageInfo getAuthList(PageInfo pageInfo, @RequestParam Map<String, Object> param) {
        return customService.getAuthList(pageInfo, param);
    }

    @RequestMapping("/getAllCusWithKf")
    public Result getAllCusWithKf(String key, String size) {
        return customService.getAllCusWithKf(key, size);
    }

    @RequestMapping("/getWorkAuthList")
    public PageInfo getWorkAuthList(PageInfo pageInfo, Custom custom) {
        return customService.getWorkAuthList(pageInfo, custom);
    }

    @RequestMapping("/passWorkAuth")
    public Result passWorkAuth(String custom_id) throws Exception {
        return customService.passWorkAuth(custom_id);
    }

    @RequestMapping("/refuseWorkAuth")
    public Result refuseWorkAuth(String custom_id) throws Exception {
        return customService.refuseWorkAuth(custom_id);
    }

    @RequestMapping("/getLinkAuthList")
    public PageInfo getLinkAuthList(PageInfo pageInfo, Custom custom) {
        return customService.getLinkAuthList(pageInfo, custom);
    }

    @RequestMapping("/passLinkAuth")
    public Result passLinkAuth(String custom_id) throws Exception {
        return customService.passLinkAuth(custom_id);
    }

    @RequestMapping("/refuseLinkAuth")
    public Result refuseLinkAuth(String custom_id) throws Exception {
        return customService.refuseLinkAuth(custom_id);
    }
}
