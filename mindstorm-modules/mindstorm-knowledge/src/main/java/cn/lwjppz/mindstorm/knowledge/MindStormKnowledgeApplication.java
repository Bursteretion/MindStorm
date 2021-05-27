package cn.lwjppz.mindstorm.knowledge;

import cn.lwjppz.mindstorm.common.mybatis.annotation.EnableCustomMybatis;
import cn.lwjppz.mindstorm.common.security.annotation.EnableCustomConfig;
import cn.lwjppz.mindstorm.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lwj
 * @since 2021-05-27
 */
@EnableCustomConfig
@EnableCustomMybatis
@EnableCustomSwagger2
@SpringBootApplication
public class MindStormKnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormKnowledgeApplication.class, args);
    }

}
