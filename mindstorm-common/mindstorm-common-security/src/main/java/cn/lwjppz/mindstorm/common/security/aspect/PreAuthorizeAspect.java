package cn.lwjppz.mindstorm.common.security.aspect;

import cn.lwjppz.mindstorm.api.permission.model.LoginUser;
import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.PreAuthorizeException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.common.security.annotation.PreAuthorize;
import cn.lwjppz.mindstorm.common.security.service.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

/**
 * <p>
 * 自定义权限切面拦截
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@Aspect
@Component
public class PreAuthorizeAspect {

    private final TokenService tokenService;

    public PreAuthorizeAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 拥有所有权限的标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    @Around("@annotation(cn.lwjppz.mindstorm.common.security.annotation.PreAuthorize)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);

        if (null == preAuthorize) {
            point.proceed();
        } else if (StringUtils.isNotEmpty(preAuthorize.hasPermission())) {
            if (hasPermission(preAuthorize.hasPermission())) {
                return point.proceed();
            }
            throw new PreAuthorizeException(ResultStatus.FORBIDDEN);
        } else if (StringUtils.isNotEmpty(preAuthorize.lacksPermission())) {
            if (lackPermission(preAuthorize.hasPermission())) {
                return point.proceed();
            }
            throw new PreAuthorizeException(ResultStatus.FORBIDDEN);
        } else if (preAuthorize.hasAnyPermission().length > 0) {
            if (hasAnyPermission(preAuthorize.hasAnyPermission())) {
                return point.proceed();
            }
            throw new PreAuthorizeException(ResultStatus.FORBIDDEN);
        } else if (StringUtils.isNotEmpty(preAuthorize.hasRole())) {
            if (hasRole(preAuthorize.hasRole())) {
                return point.proceed();
            }
            throw new PreAuthorizeException(ResultStatus.FORBIDDEN);
        } else if (StringUtils.isNotEmpty(preAuthorize.lacksRole())) {
            if (lacksRole(preAuthorize.lacksRole())) {
                return point.proceed();
            }
            throw new PreAuthorizeException(ResultStatus.FORBIDDEN);
        } else if (preAuthorize.hasAnyRoles().length > 0) {
            if (hasAnyRoles(preAuthorize.hasAnyRoles())) {
                return point.proceed();
            }
            throw new PreAuthorizeException(ResultStatus.FORBIDDEN);
        }

        return point.proceed();
    }

    /**
     * 验证用户是否拥有某权限
     *
     * @param permission 权限字符串
     * @return 用户是否有某权限
     */
    private boolean hasPermission(String permission) {
        LoginUser loginUser = tokenService.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 权限列表
     * @return 用户是否具有以下任意一个权限
     */
    private boolean hasAnyPermission(String[] permissions) {
        LoginUser loginUser = tokenService.getLoginUser();

        if (ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }

        Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions) {
            if (StringUtils.isNotEmpty(permission) && authorities.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含某权限
     *
     * @param authorities 权限列表
     * @param permission  权限字符串
     * @return 是否包含此权限
     */
    private boolean hasPermissions(Collection<String> authorities, String permission) {
        return authorities.stream()
                .filter(StringUtils::isNotEmpty)
                .anyMatch(v -> ALL_PERMISSION.contains(v) || PatternMatchUtils.simpleMatch(v, permission));
    }

    /**
     * 验证用户是否不具备某权限
     *
     * @param permission 权限字符串
     * @return 是否不具备此权限
     */
    private boolean lackPermission(String permission) {
        return !hasPermission(permission);
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色字符串
     * @return 用户是否具备某角色
     */
    private boolean hasRole(String role) {
        LoginUser loginUser = tokenService.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }

        Set<String> roles = loginUser.getRoles();
        for (String roleKey : roles) {
            if (SUPER_ADMIN.equals(roleKey) || roleKey.equals(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否不具备某角色，与 hasRole 逻辑相反。
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    public boolean lacksRole(String role) {
        return !hasRole(role);
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roles 角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoles(String[] roles) {
        LoginUser loginUser = tokenService.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }

        for (String role : roles) {
            if (hasRole(role)) {
                return true;
            }
        }

        return false;
    }
}
