package com.hohai.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hohai.pojo.User;
import com.hohai.service.UserService;
import com.hohai.mapper.UserMapper;
import com.hohai.utils.JwtHelper;
import com.hohai.utils.MD5Util;
import com.hohai.utils.Result;
import com.hohai.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author feiqiua
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2023-10-28 19:23:10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录实现
     * 1. 根据账号查询数据库，返回用户数据，若id为空，则账号错误
     * 2. 查到用户数据之后，若密码相同则正确，否则密码错误
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        // 账号错误，user数据为空
        if (loginUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        // 密码不为空且正确，生成token并返回
        if (!StringUtils.isEmpty(user.getUserPwd())
                && loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd()))){
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));

            Map<Object, Object> map = new HashMap<>();
            map.put("token",token);
            return Result.ok(map);
        }

        // 密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 根据用户传来的 token 查询用户数据
     * 1. 判断 token 的时间是否在有效时间内
     * 2. 查询数据库，把得到的密码去掉
     * 3. 包装好之后返回给前端
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        if (jwtHelper.isExpiration(token)){
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userId);
        if (user != null){
            user.setUserPwd("");
            Map<String, User> map = new HashMap<>();
            map.put("loginUser",user);
            return Result.ok(map);
        }
        else
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

    /**
     * 检查用户名是否可用
     * 1. 根据用户名查询
     * 2. 若 count == 0, 可用 ;
     * 3. 若 count > 0, 不可用
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        Long aLong = userMapper.selectCount(queryWrapper);
        if (aLong == 0) {
            return Result.ok(null);
        }
        return Result.build(null,ResultCodeEnum.USERNAME_USED);
    }

    /**
     * 用户注册
     * 1. 判断用户名是否可用
     * 2. 密码加密
     * 3. 保存用户信息
     * 4. 返回结果
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {
        Result result = checkUserName(user.getUsername());
        if (result.getCode() == 505){
            return result;
        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int insert = userMapper.insert(user);
        System.out.println("rows = " + insert);
        return Result.ok(null);
    }
}




