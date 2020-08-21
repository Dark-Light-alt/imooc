package com.imooc.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("classpath:application.yml")
public class JwtUtils {

    // sub 存储用户信息
    private static final String CLAIM_KEY_USERNAME = "sub";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据负载信息生成 token
     *
     * @param claims 负载信息
     * @return token
     */
    private String generateToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim(CLAIM_KEY_USERNAME, String.valueOf(claims.get(CLAIM_KEY_USERNAME)))
                .withIssuedAt(new Date())
                .withExpiresAt(generateExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 生成 token 过期时间
     *
     * @return date
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 根据 token 进行验证并解析数据
     *
     * @param token
     * @return 解析过的 JWT，可直接通过此对象获取存储的信息
     * @throws TokenExpiredException token 过期异常
     * @throws JWTDecodeException    解析异常
     */
    private DecodedJWT getDecodeJWTFromToken(String token) throws TokenExpiredException, JWTDecodeException, Exception {

        // 构建 token 验签对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();

        // 对 token 进行验证并解析
        DecodedJWT verify = jwtVerifier.verify(token);

        return verify;
    }

    /**
     * 根据 token 获取到用户名
     *
     * @param token
     * @return 用户名
     * @throws TokenExpiredException token 过期异常
     * @throws JWTDecodeException    解析异常
     */
    public String getUsernameFromToken(String token) throws TokenExpiredException, JWTDecodeException, Exception {

        DecodedJWT decodedJWT = getDecodeJWTFromToken(token);

        Claim claim = decodedJWT.getClaim(CLAIM_KEY_USERNAME);

        return claim.asString();
    }

    /**
     * 根据用户名生成 token
     *
     * @param username 用户名
     * @return token
     */
    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put(CLAIM_KEY_USERNAME, username);

        return generateToken(claims);
    }
}
