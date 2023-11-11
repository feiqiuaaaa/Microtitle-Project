package com.hohai.service;

import com.hohai.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hohai.pojo.vo.PortalVo;
import com.hohai.utils.Result;

/**
* @author feiqiua
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2023-10-28 19:23:10
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline, String token);

    Result updateData(Headline headline);
}
