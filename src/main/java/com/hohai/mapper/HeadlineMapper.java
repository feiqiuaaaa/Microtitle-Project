package com.hohai.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hohai.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hohai.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
* @author feiqiua
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2023-10-28 19:23:10
* @Entity com.hohai.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    Map selectDetailMap(Integer hid);

    IPage<Map> selectPageMap(IPage page, @Param("portalVo") PortalVo portalVo);
}




