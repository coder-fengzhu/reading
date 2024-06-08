package com.fengzhu.reading.utils;

import com.alibaba.google.common.collect.Maps;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("jwt.token.secretkey")
    private String secretKey;

    public String getToken(String userId, String userName) {
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("userId", userId)
                .withClaim("userName", userName)
                .withClaim("timeStamp", Instant.now().toEpochMilli())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Map<String, String> parseToken(String token) {
        HashMap<String, String> map = Maps.newHashMap();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);

        map.put("userId", decodedjwt.getClaim("userId").asString());
        map.put("userName", decodedjwt.getClaim("userName").asString());
        map.put("timeStamp", decodedjwt.getClaim("timeStamp").asLong().toString());
        return map;
    }
}
