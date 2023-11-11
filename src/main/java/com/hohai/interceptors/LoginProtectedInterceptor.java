package com.hohai.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hohai.utils.JwtHelper;
import com.hohai.utils.Result;
import com.hohai.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 在进行头条操作之前，都要被拦截，进行登录检查
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 request 接收 token，检查 token 是否有效
        String token = request.getHeader("token");
        boolean expiration = jwtHelper.isExpiration(token);

        // 有效 放行
        if (!expiration){
            return true;
        }
        // 无效 从 response 返回 json 数据：报错 504
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(Result.build(null, ResultCodeEnum.NOTLOGIN));
        response.getWriter().print(json);
        return false;
    }
}
