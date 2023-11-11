package com.hohai.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hohai.pojo.Headline;
import com.hohai.pojo.vo.PortalVo;
import com.hohai.service.HeadlineService;
import com.hohai.mapper.HeadlineMapper;
import com.hohai.utils.JwtHelper;
import com.hohai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author feiqiua
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2023-10-28 19:23:10
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private HeadlineMapper headlineMapper;

    /**
     * 首页分页数据查询
     * 1. 设置分页参数
     * 2. 进行分页查询
     * 3. 返回分页数据
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {

        // 1.设置分页参数
        IPage page = new Page(portalVo.getPageNum(),portalVo.getPageSize());

        // 2.查询分页数据
        IPage<Map> mapIPage = headlineMapper.selectPageMap(page, portalVo);

        // 3.结果封装
        Map<String,Object> pageInfo = new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());

        Map<String,Map<String,Object>> data = new HashMap<>();
        data.put("pageInfo",pageInfo);

        return Result.ok(data);
    }

    /**
     * 头条详情查询
     * 1. 查询数据（多表）
     * 2. 访问量 +1 （乐观锁）
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        // 1.查询数据
        Map map = headlineMapper.selectDetailMap(hid);

        // 2.将数据库中的浏览量 +1 ，更新版本号
        Headline headline = new Headline();
        headline.setHid((Integer) map.get("hid"));
        headline.setVersion((Integer) map.get("version"));
        headline.setPageViews((Integer) map.get("pageViews") + 1);
        int rows = headlineMapper.updateById(headline);
        System.out.println("rows = " + rows);

        // 3.封装数据
        Map<Object, Object> headlineDetail = new HashMap<>();
        headlineDetail.put("headline",map);

        return Result.ok(headlineDetail);
    }

    /**
     * 头条发布功能
     * 1. 先检查是否已经登录
     * 2. 补全插入所需的属性，并插入数据库 publisher pageViews createTime updateTime
     * 3. 结果封装
     * @param headline
     * @param token
     * @return
     */
    @Override
    public Result publish(Headline headline, String token) {
        // 检查交给拦截器
        // 补全属性 + 插入数据库
        int publisherId = jwtHelper.getUserId(token).intValue();
        headline.setPublisher(publisherId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    /**
     * 头条修改
     * @param headline
     * @return
     */
    @Override
    public Result updateData(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version);   // 乐观锁
        headline.setUpdateTime(new Date());
        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




