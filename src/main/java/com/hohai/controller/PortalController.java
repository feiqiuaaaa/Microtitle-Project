package com.hohai.controller;

import com.hohai.mapper.TypeMapper;
import com.hohai.pojo.Headline;
import com.hohai.pojo.vo.PortalVo;
import com.hohai.service.HeadlineService;
import com.hohai.service.TypeService;
import com.hohai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private HeadlineService headlineService;

    @Autowired
    private TypeService typeService;

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        Result result = typeService.findAllTypes();
        return result;
    }

    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody  PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);
        return result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }
}
