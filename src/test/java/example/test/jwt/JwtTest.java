package example.test.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class JwtTest {

    @DisplayName("1. jjwt를 이용한 토큰 테스트")
    @Test
    void test_1() {
        String okta_token = Jwts.builder().addClaims(
                Map.of("name", "jeonggi", "price", 3000))
                .signWith(SignatureAlgorithm.HS256, "jeonggi")
                .compact();

        System.out.println("okta_token = " + okta_token);
    }

    @DisplayName("1. java-jwt를 이용한 토큰 테스트")
    @Test
    void test_2() {

    }
}
