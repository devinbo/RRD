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
    public Result delCustom(String customIds) {
        return customService.delCustom(customIds);
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


}
