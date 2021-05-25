package cn.lwjppz.mindstorm.permission;

import cn.lwjppz.mindstorm.common.mybatis.annotation.EnableCustomMybatis;
import cn.lwjppz.mindstorm.common.security.annotation.EnableCustomConfig;
import cn.lwjppz.mindstorm.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lwj
 * @since 2021-05-10
 */
@EnableCustomConfig
@EnableDiscoveryClient
@EnableCustomMybatis
@EnableCustomSwagger2
@SpringBootApplication
public class MindStormPermissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormPermissionApplication.class, args);
    }

}
