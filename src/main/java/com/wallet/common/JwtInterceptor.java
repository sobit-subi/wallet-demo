package com.wallet.common;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wallet.entity.User;
import com.wallet.exception.ServiceException;
import com.wallet.repository.UserMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


public class JwtInterceptor implements HandlerInterceptor {

    private final UserMapper userMapper;

    public JwtInterceptor(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // pass login/register urls, token will be generated in these requests!
//        if (request.getRequestURI().contains("/api/user/login")
//                || request.getRequestURI().contains("/api/user/register")) {
//            return true;
//        }

        // pass OPTIONS method
//        String method = request.getMethod();
//        if (method.equals("OPTIONS")) {
//            return true;
//        }

        // if token is empty in header params, then get from body params

        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }

        String token = request.getHeader("token");

        if (StringUtils.isEmpty(token)) {
            System.out.println("header is empty");
            token = request.getParameter("token");
        }

        // if token is still empty in body param, return exception with #401
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.toString(), "Please login!");
        }

        // get user id from token [{id}.{password}.{key}]
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            System.out.println("userId = " + userId);
        } catch (JWTDecodeException e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.toString(), "Please login!");
        }

        // check user data by id from database
        User user = userMapper.findUserByUserId(userId);
        if (user == null) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.toString(), "Please login!");
        }

        // by user password encryption, generate a verifier
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            // verify token
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.toString(), "Please login!");
        }

        return true;
    }
}
