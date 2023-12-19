package com.yuki.Utils;

//import com.sun.tools.sjavac.Log;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import jdk.internal.org.jline.utils.Log;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "yuki.jwt")
public class JwtUtils {

    private String secret;
    private long expire;
    private String header;

    /*
     * 生成jwt token
     * */

    public String generateToken(long userid){
        Date nowDate = new Date();
        // 过期时间
        Date expireDate = new Date(nowDate.getTime()+expire*1000);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(userid+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimByToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
//             Log.debug("validate is token error");
            System.out.println("validate is token error");
            return null;
        }
    }

    public boolean isTokenExpired(Date expiration){
        return expiration.before(new Date());
    }

}
