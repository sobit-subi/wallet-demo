package com.wallet.util;

import ch.qos.logback.core.util.StringUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wallet.entity.User;
import com.wallet.exception.ServiceException;
import com.wallet.repository.UserMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Component
public class TokenUtils {
    private static UserMapper staticUserMapper;

    @Resource
    UserMapper userMapper;

    @PostConstruct
    public void setUserService() {
        staticUserMapper = userMapper;
    }

    // generate new token available for 2 hours from now
    public static String generateToken(String userId, String sign) {
        return JWT.create().withAudience(userId)  // user id save in token as audience
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // token expires after 2 hours
                .sign(Algorithm.HMAC256(sign));   // password is token's secret key
    }

    // get current login user information
    public static boolean validateToken(String userId, String token) {
        boolean valid = false;
        try {
            if (StringUtil.notNullNorEmpty(token)) {
                System.out.println("token:" + token);
                User user = staticUserMapper.findUserByUserId(userId);
                valid = user != null && user.getToken().equals(token);
            }
        } catch (Exception e) {
            throw new ServiceException("token is invalid or expired");
        }

        return valid;
    }

}
