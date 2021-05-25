package cn.lwjppz.mindstorm.common.security.annotation;

import cn.lwjppz.mindstorm.common.security.feign.FeignAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * <p>
 * 开启默认配置注解
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableAspectJAutoProxy(exposeProxy = true)
// 开启线程异步执行
@EnableAsync
@Import(FeignAutoConfiguration.class)
public @interface EnableCustomConfig {
}
