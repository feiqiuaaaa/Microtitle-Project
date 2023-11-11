package com.hohai.service;

import com.hohai.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hohai.utils.Result;

/**
* @author feiqiua
* @description 针对表【news_user】的数据库操作Service
* @createDate 2023-10-28 19:23:10
*/
public interface UserService extends IService<User> {

    Result login(User user);

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);
}
