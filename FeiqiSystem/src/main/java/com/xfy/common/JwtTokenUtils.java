/**
 * JwtTokenUtils
 *
 * @author Feiqi
 * @date 2024/10/08  下午 2:53
 */
package com.xfy.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xfy.entity.User;
import com.xfy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenUtils {

    private static UserService staticUserService;
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    /*
     * 生成token
     * */
    public static String generateToken(String userId, String password) {
        return JWT.create().withAudience(userId) //将userId保存到token里面作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(password)); // 以password为token的密钥
    }

    /*
     * 获取当前登录的用户信息
     * */
    public static User getCurrentUser() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("获取当前登录的token失败！token: {}", token);
                return null;
            }
            /*解析token，获取用户id*/
            String userid = JWT.decode(token).getAudience().get(0);
            return staticUserService.findUserById(Integer.valueOf(userid));
        } catch (Exception e) {
            log.error("获取当前登录的用户信息失败！token: {}", token, e);
            return null;
        }
    }
}