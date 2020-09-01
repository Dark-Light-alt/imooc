package com.imooc.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Component
//@PropertySource("classpath:application.yml")
public class JwtUtils {

//    // sub 存储用户信息
//    private static final String CLAIM_KEY_USERNAME = "sub";
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration}")
//    private Long expiration;
//
//    /**
//     * 根据负载信息生成 token
//     *
//     * @param claims 负载信息
//     * @return token
//     */
//    private String generateToken(Map<String, Object> claims) {
//        return JWT.create()
//                .withClaim(CLAIM_KEY_USERNAME, String.valueOf(claims.get(CLAIM_KEY_USERNAME)))
//                .withIssuedAt(new Date    ())
//                .withExpiresAt(generateExpirationDate())
//                .sign(Algorithm.HMAC256(secret));
//    }
//
//    /**
//     * 生成 token 过期时间
//     *
//     * @return date
//     */
//    private Date generateExpirationDate() {
//        return new Date(System.currentTimeMillis() + expiration * 1000);
//    }
//
//    /**
//     * 根据 token 进行验证并解析数据
//     *
//     * @param token
//     * @return 解析过的 JWT，可直接通过此对象获取存储的信息
//     * @throws TokenExpiredException token 过期异常
//     * @throws JWTDecodeException    解析异常
//     */
//    private DecodedJWT getDecodeJWTFromToken(String token) throws TokenExpiredException, JWTDecodeException, Exception {
//
//        // 构建 token 验签对象
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
//
//        // 对 token 进行验证并解析
//        DecodedJWT verify = jwtVerifier.verify(token);
//
//        return verify;
//    }
//
//    /**
//     * 根据 token 获取到用户名
//     *
//     * @param token
//     * @return 用户名
//     * @throws TokenExpiredException token 过期异常
//     * @throws JWTDecodeException    解析异常
//     */
//    public String getUsernameFromToken(String token) throws TokenExpiredException, JWTDecodeException, Exception {
//
//        DecodedJWT decodedJWT = getDecodeJWTFromToken(token);
//
//        Claim claim = decodedJWT.getClaim(CLAIM_KEY_USERNAME);
//
//        return claim.asString();
//    }
//
//    /**
//     * 根据用户名生成 token
//     *
//     * @param username 用户名
//     * @return token
//     */
//    public String generateToken(String username) {
//
//        Map<String, Object> claims = new HashMap<String, Object>();
//
//        claims.put(CLAIM_KEY_USERNAME, username);
//
//        return generateToken(claims);
//    }

    private static final String AUTHORITIES_KEY = "auth";

    // 签名秘钥
    private String secretKey;

    // 失效日期
    private long tokenValidityInMilliseconds;

    // （记住我）失效日期
    private long tokenValidityInMillisecondsForRememberMe;

    @PostConstruct
    public void init() {
        this.secretKey = "zhaohaobomiyao";
        int secondInOneday = 1000 * 60 * 60 * 24;
        this.tokenValidityInMilliseconds = secondInOneday * 2L;
        this.tokenValidityInMillisecondsForRememberMe = secondInOneday * 7L;
    }

    /**
     * @param authentication 权限信息
     * @param rememberMe     是否记住我
     * @return
     */
    public String createToken(Authentication authentication, Boolean rememberMe) {

        // 获取用户权限字符串，如 delete，update
        String authentications = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date validity;

        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        } else {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        }

        return JWT.create()
                .withSubject(authentication.getName())
                .withClaim(AUTHORITIES_KEY, authentications)
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication getAuthentication(String token) {

        System.out.println(token);

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        Claim authenticationClaim = decodedJWT.getClaim(AUTHORITIES_KEY);

        Arrays.stream(authenticationClaim.toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User user = new User(decodedJWT.getSubject(), "", null);
        return new UsernamePasswordAuthenticationToken(user, "", null);
    }

    /**
     * 验证 token
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {

        try {

            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return true;

        } catch (TokenExpiredException e) {
            System.out.println("token 过期");
        } catch (JWTDecodeException e) {
            System.out.println("token 解析失败");
        } catch (Exception e) {
            System.out.println("错误了");
        }

        return false;
    }
}
