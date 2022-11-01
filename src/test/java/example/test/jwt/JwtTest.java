package example.test.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JwtTest {

    private void printToken(String token) {
        String[] tokens = token.split("\\.");
        System.out.println("header = " + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("body  = " + new String(Base64.getDecoder().decode(tokens[1])));
    }

    @DisplayName("1. jjwt를 이용한 토큰 테스트")
    @Test
    void test_1() {
        String okta_token = Jwts.builder().addClaims(
                Map.of("name", "jeonggi", "price", 3000))
                .signWith(SignatureAlgorithm.HS256, "jeonggi")
                .compact();

        printToken(okta_token);

        Jws<Claims> tokenInfo = Jwts.parser()
                .setSigningKey("jeonggi")
                .parseClaimsJws(okta_token);

        System.out.println("body = " + tokenInfo);
    }

    @DisplayName("1. java-jwt를 이용한 토큰 테스트")
    @Test
    void test_2() {
        String sign = JWT.create()
                .withClaim("name", "jeonggi")
                .withClaim("price", "3000")
                .sign(Algorithm.HMAC256("jeonggi"));

        printToken(sign);

        DecodedJWT tokenInfo = JWT.require(Algorithm.HMAC256("jeonggi")).build().verify(sign);
        System.out.println("tokenInfo = " + tokenInfo.getClaims());

    }

    @DisplayName("3. 만료 시간 테스트")
    @Test
    void test_3() throws InterruptedException {
        final Algorithm Al = Algorithm.HMAC256("jeonggi");
        String token = JWT.create()
                .withSubject("a1234")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000))
                .sign(Al);

        Thread.sleep(2000);
        DecodedJWT verify = JWT.require(Al)
                .build()
                .verify(token);

        System.out.println(verify.getClaims());
    }
}
