package cn.lwjppz.mindstorm.user;

import cn.lwjppz.mindstorm.common.mybatis.annotation.EnableCustomMybatis;
import cn.lwjppz.mindstorm.common.security.annotation.EnableCustomConfig;
import cn.lwjppz.mindstorm.common.security.annotation.EnableMsFeignClients;
import cn.lwjppz.mindstorm.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lwj
 */
@EnableMsFeignClients
@EnableCustomMybatis
@EnableCustomConfig
@EnableCustomSwagger2
@SpringBootApplication
public class MindStormUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormUserApplication.class, args);
    }

}
