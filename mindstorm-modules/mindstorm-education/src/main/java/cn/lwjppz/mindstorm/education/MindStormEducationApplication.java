package cn.lwjppz.mindstorm.education;

import cn.lwjppz.mindstorm.common.mybatis.annotation.EnableCustomMybatis;
import cn.lwjppz.mindstorm.common.security.annotation.EnableCustomConfig;
import cn.lwjppz.mindstorm.common.security.annotation.EnableMsFeignClients;
import cn.lwjppz.mindstorm.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lwj
 * @since 2021-06-07
 */
@EnableMsFeignClients
@EnableCustomMybatis
@EnableCustomConfig
@EnableCustomSwagger2
@SpringBootApplication
@EnableTransactionManagement
public class MindStormEducationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormEducationApplication.class, args);
    }

}
