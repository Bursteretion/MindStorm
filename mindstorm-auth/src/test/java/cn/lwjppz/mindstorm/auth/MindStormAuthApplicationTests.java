package cn.lwjppz.mindstorm.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootTest
class MindStormAuthApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testPassword() {
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
    }
    
}
