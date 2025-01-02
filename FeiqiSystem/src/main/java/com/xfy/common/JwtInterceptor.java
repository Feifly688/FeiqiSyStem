/**
 * JwtInterceptor
 *
 * @author Feiqi
 * @date 2024/10/08  下午 4:02
 */
package com.xfy.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xfy.entity.User;
import com.xfy.exception.CustomException;
import com.xfy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* JWT拦截器
* */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /*1.从Http请求的header中获取token*/
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            /*如果没拿到，再去参数里面拿*/
            token = request.getParameter("token");
        }

        /*2.开始执行认证*/
        if (StrUtil.isBlank(token)) {
            throw new CustomException("没有token，请重新登录");
        }

        /*获取token中的userId*/
        String userId;
        User user;
        try {           /*decode解码    getAudience获得id  */
            userId = JWT.decode(token).getAudience().get(0);
            /*根据token中的userid查询数据库*/
            user = userService.findUserById(Integer.parseInt(userId));
        } catch (Exception e) {
            String errMsg = "token验证失败，请重新登录！";
            log.error(errMsg + ", token=" + token, e);
            throw new CustomException(errMsg);
        }
        if (user == null) {
            throw new CustomException("用户不存在，请重新登录");
        }
        try {
            /*用户密码加签验证token*/
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            jwtVerifier.verify(token); //验证token
        } catch (JWTVerificationException e) {
            throw new CustomException("token验证失败，请重新登录");
        }
        return true;
    }

}
