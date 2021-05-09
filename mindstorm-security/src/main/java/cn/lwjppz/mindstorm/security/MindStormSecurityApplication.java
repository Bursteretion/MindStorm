package cn.lwjppz.mindstorm.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lwj
 * @since 2021-05-09
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("cn.lwjppz.mindstorm.security")
public class MindStormSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormSecurityApplication.class, args);
    }

}
