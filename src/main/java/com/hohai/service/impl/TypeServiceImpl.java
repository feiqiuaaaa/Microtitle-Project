package com.hohai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hohai.pojo.Type;
import com.hohai.service.TypeService;
import com.hohai.mapper.TypeMapper;
import com.hohai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author feiqiua
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2023-10-28 19:23:10
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 查询首页所有类别
     * 1. 查询数据库表中所有类别
     * 2. 返回结果
     * @return
     */
    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }
}




