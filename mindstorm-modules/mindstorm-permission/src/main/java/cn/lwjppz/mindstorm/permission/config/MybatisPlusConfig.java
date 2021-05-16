package cn.lwjppz.mindstorm.permission.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
@Configuration
@EnableTransactionManagement
@MapperScan("cn.lwjppz.mindstorm.permission.mapper")
public class MybatisPlusConfig {

}
