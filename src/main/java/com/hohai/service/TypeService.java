package com.hohai.service;

import com.hohai.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hohai.utils.Result;

/**
* @author feiqiua
* @description 针对表【news_type】的数据库操作Service
* @createDate 2023-10-28 19:23:10
*/
public interface TypeService extends IService<Type> {

    /**
     * 查询首页所有类别
     * @return
     */
    Result findAllTypes();
}
