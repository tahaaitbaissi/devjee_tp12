package org.example.jwt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void genereEtExtraitLeLogin() {
        String token = jwtUtil.generateToken("hamza");

        assertEquals("hamza", jwtUtil.extractUsername(token));
    }

    @Test
    public void tokenValideEstAccepte() {
        assertTrue(jwtUtil.validateToken(jwtUtil.generateToken("salma")));
    }

    @Test
    public void tokenAltereEstRefuse() {
        String token = jwtUtil.generateToken("hamza");

        assertFalse(jwtUtil.validateToken(token + "x"));
    }

    @Test
    public void tokenMalFormeEstRefuse() {
        assertFalse(jwtUtil.validateToken("abc.def.ghi"));
    }

    @Test
    public void tokenExpireEstRefuse() {
        JwtUtil expire = new JwtUtil("Tp12JwtSecretKey2026SecuriteApiMliaeduChangeMe", -1000);
        String token = expire.generateToken("hamza");

        assertFalse(expire.validateToken(token));
    }
}
