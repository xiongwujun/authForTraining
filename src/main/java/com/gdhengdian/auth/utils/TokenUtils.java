package com.gdhengdian.auth.utils;

import com.gdhengdian.auth.common.HttpStateEnum;
import com.gdhengdian.auth.exception.AuthException;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

/**
 * token的键说明：
 * 1. ISSUER : 签发者
 * 2. SUBJECT: 面向的用户
 * 3. AUDIENCE: 接收方
 * 4. EXPIRATION: 过期时间
 * 5. Not Before: 生效时间
 * 6. ISSUED AT:签发时间
 * 7. JWT_ID:　jwt的唯一身份标识，主要用来作为一次性token,从而回避重放工具
 *
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-15
 */
public final class TokenUtils {
    /**
     * 秘钥，请勿泄露，请勿随便修改 backups: fhdsfAs,sD1hdfg213hfdsuif>sdad,
     */
    private final static String SECRET = "fhdsfAs,sD1hdfg213hfdsuif>sdad";
    /**
     * 签发者
     */
    private final static String ISSUER = "www.gdhengdian.com";

    public static String createAccessTokenByAccessTokenId(String accessTokenId) {

        // 签发时间,JDK1.8后出的时间类
        Instant issuedAt = Instant.now();
        //１２个小时后过期
        Instant expireAt = issuedAt.plusSeconds(53200L);
        
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;	
        //将秘钥进行base64解码
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        //用秘钥对这个
        Key signingKey = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());
        
        JwtBuilder builder = Jwts.builder()	
                .setId(accessTokenId)
                .setIssuedAt(Date.from(issuedAt))
                .setIssuer(ISSUER)
                .setExpiration(Date.from(expireAt))
                .signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    public static String getAccessTokenIdByAccessToken(String accessToken) {
        Claims body;
        try {
            body = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET)).parseClaimsJws(accessToken).getBody();
        } catch (Exception e) {
            throw new AuthException(HttpStateEnum.UNAUTHORIZED);
        }
        return body.getId();
    }

}
