package cn.lwjppz.mindstorm.security.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
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
@MapperScan("cn.lwjppz.mindstorm.security.mapper")
public class MybatisPlusConfig {

}
