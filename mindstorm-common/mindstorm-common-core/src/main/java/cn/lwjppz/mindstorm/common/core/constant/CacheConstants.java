package cn.lwjppz.mindstorm.common.core.constant;

/**
 * <p>
 * 缓存的 key 常量
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
public class CacheConstants {

    private CacheConstants() {
    }

    /**
     * 令牌自定义标识
     */
    public static final String HEADER = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 权限缓存前缀
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 授权信息字段
     */
    public static final String AUTHORIZATION_HEADER = "authorization";
}
