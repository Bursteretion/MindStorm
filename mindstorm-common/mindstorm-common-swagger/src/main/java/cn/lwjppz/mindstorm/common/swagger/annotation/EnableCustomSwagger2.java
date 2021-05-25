package cn.lwjppz.mindstorm.common.swagger.annotation;

import cn.lwjppz.mindstorm.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 是否开启Swagger2文档注解
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableCustomSwagger2 {
}
