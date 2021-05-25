package cn.lwjppz.mindstorm.common.security.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * <p>
 * 自定义feign注解, 添加basePackages路径
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableMsFeignClients {

    String[] value() default {};

    String[] basePackages() default {"cn.lwjppz"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
