package cn.lwjppz.mindstorm.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 自定义权限注解
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {

    /**
     * 验证用户是否具备某权限
     */
    String hasPermission() default "";

    /**
     * 验证用户是否不具备某权限，与 hasPermission 逻辑相反
     */
    String lacksPermission() default "";

    /**
     * 验证用户是否具有以下任意一个权限
     */
    String[] hasAnyPermission() default {};

    /**
     * 判断用户是否拥有某个角色
     */
    String hasRole() default "";

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反
     */
    String lacksRole() default "";

    /**
     * 验证用户是否具有以下任意一个角色
     */
    String[] hasAnyRoles() default {};
}
