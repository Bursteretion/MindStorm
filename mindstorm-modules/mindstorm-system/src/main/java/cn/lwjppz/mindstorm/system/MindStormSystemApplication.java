package cn.lwjppz.mindstorm.system;

import cn.lwjppz.mindstorm.common.mybatis.annotation.EnableCustomMybatis;
import cn.lwjppz.mindstorm.common.security.annotation.EnableCustomConfig;
import cn.lwjppz.mindstorm.common.security.annotation.EnableMsFeignClients;
import cn.lwjppz.mindstorm.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lwj
 * @since 2021-05-30
 */
@EnableCustomConfig
@EnableMsFeignClients
@EnableCustomMybatis
@EnableCustomSwagger2
@SpringBootApplication
public class MindStormSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormSystemApplication.class, args);
    }

}
