package org.scoula.security.util;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class })
@Log4j
class JwtProcessorTest {
    @Autowired
    JwtProcessor jwtProcessor;

    @Test
    void generateToken() {
        String username = "user0";
        String token = jwtProcessor.generateToken(username);
        log.info(token);
        assertNotNull(token);
    }

    @Test
    void getUsername() {
        String token =
"eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTcyNDIwNjU4MiwiZXhwIjoxNzI0MjA2ODgyfQ.PdoRPU91UH7rohhADSNjBRcrjnfY39Mi2PSuAPTk0zPxot1jvF8WaMBaCRSdQFKj";

        String username = jwtProcessor.getUsername(token);
        log.info(username);
        assertNotNull(username);
    }

    @Test
    void validateToken() {
        String token =
"eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTcyNDIwNjU4MiwiZXhwIjoxNzI0MjA2ODgyfQ.PdoRPU91UH7rohhADSNjBRcrjnfY39Mi2PSuAPTk0zPxot1jvF8WaMBaCRSdQFKj";

        boolean isValid = jwtProcessor.validateToken(token);
        log.info(isValid);
        assertTrue(isValid);
    }
}