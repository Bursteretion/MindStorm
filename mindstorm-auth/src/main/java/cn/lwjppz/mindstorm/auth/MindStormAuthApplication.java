package cn.lwjppz.mindstorm.auth;

import cn.lwjppz.mindstorm.common.security.annotation.EnableMsFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author lwj
 * @since 2021-05-15
 */
@EnableMsFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MindStormAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormAuthApplication.class, args);
    }

}
