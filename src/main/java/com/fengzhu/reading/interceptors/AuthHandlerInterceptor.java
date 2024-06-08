package com.fengzhu.reading.interceptors;

import com.fengzhu.reading.exceptions.TokenAuthExpiredException;
import com.fengzhu.reading.utils.BaseContext;
import com.fengzhu.reading.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Map;

@Component
@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.token.refreshTime}")
    private Long refreshTime;

    @Value("${jwt.token.expiresTime}")
    private Long expiresTime;
    /**
     * 权限认证的拦截操作.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                log.info("=======进入拦截器========");
        // 如果不是映射到方法直接通过,可以访问资源.
        log.info("request url: " + request.getRequestURI());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //为空就返回错误
        String token = request.getHeader("token");
        if (null == token || "".equals(token.trim())) {
            return false;
        }
        log.info("==============token:" + token);
        Map<String, String> map = jwtUtils.parseToken(token);
        String userId = map.get("userId");
        String userName = map.get("userName");

        BaseContext.setCurrentId(Long.valueOf(userId));
        long timeOfUse = Instant.now().toEpochMilli() - Long.parseLong(map.get("timeStamp"));
        //1.判断 token 是否过期
        if (timeOfUse < refreshTime) {
            log.info("token验证成功");
            return true;
        }
        //超过token刷新时间，刷新 token
        else if (timeOfUse >= refreshTime && timeOfUse < expiresTime) {
            response.setHeader("token", jwtUtils.getToken(userId, userName));
            log.info("token刷新成功");
            return true;
        }
        //token过期就返回 token 无效.
        else {
            throw new TokenAuthExpiredException("token is invalid");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        BaseContext.removeCurrentId();
    }
}
