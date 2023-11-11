package com.hohai.controller;

import com.hohai.pojo.User;
import com.hohai.service.UserService;
import com.hohai.utils.JwtHelper;
import com.hohai.utils.Result;
import com.hohai.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        return result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }

    /**
     * 检查是否已经登录，检验 token
     * @param token
     * @return
     */
    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        boolean expiration = jwtHelper.isExpiration(token);     // 有效 false  无效 true
        if (expiration){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        else return Result.ok(null);
    }
}
