package cn.lwjppz.mindstorm.common.mybatis.annotation;

import cn.lwjppz.mindstorm.common.mybatis.config.MybatisPlusConfig;
import cn.lwjppz.mindstorm.common.mybatis.hanlder.MyBatisPlusMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * Mybatis 自动配置
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@MapperScan("cn.lwjppz.mindstorm.**.mapper")
@Import({MybatisPlusConfig.class, MyBatisPlusMetaObjectHandler.class})
public @interface EnableCustomMybatis {
}
