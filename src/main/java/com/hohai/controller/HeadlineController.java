package com.hohai.controller;

import com.hohai.pojo.Headline;
import com.hohai.service.HeadlineService;
import com.hohai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadlineController {

    @Autowired
    private HeadlineService headlineService;

    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline,@RequestHeader String token){
        Result result = headlineService.publish(headline,token);
        return result;
    }

    /**
     * 头条修改回显
     * @param hid
     * @return
     */
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        // 直接用 service 层的方法
        Headline byId = headlineService.getById(hid);
        // 封装数据
        Map map = new HashMap<>();
        map.put("headline",byId);

        return Result.ok(map);
    }

    @PostMapping("update")
    public Result update(@RequestBody Headline headline){
        Result result = headlineService.updateData(headline);
        return result;
    }

    /**
     * 头条删除
     * @param hid
     * @return
     */
    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid){
        headlineService.removeById(hid);
        return Result.ok(null);
    }
}
