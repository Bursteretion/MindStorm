package cn.lwjppz.mindstorm.common.security.service;

import cn.lwjppz.mindstorm.api.permission.model.Loginuser;
import cn.lwjppz.mindstorm.common.core.constant.CacheConstants;
import cn.lwjppz.mindstorm.common.core.constant.Constants;
import cn.lwjppz.mindstorm.common.core.utils.IdUtils;
import cn.lwjppz.mindstorm.common.core.utils.IpUtils;
import cn.lwjppz.mindstorm.common.core.utils.SecurityUtils;
import cn.lwjppz.mindstorm.common.core.utils.ServletUtils;
import cn.lwjppz.mindstorm.common.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Token 验证处理
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@Component
public class TokenService {

    private final RedisService redisService;

    public TokenService(@Lazy RedisService redisService) {
        this.redisService = redisService;
    }

    private static final long EXPIRE_TIME = Constants.TOKEN_EXPIRE * 60;

    private static final String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    protected static final long MILLIS_SECOND = 1000;

    public Map<String, Object> createToken(Loginuser loginUserDTO) {
        // 生成token
        String token = IdUtils.fastUUID();
        loginUserDTO.setToken(token);
        loginUserDTO.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));

        refreshToken(loginUserDTO);

        // 保存或更新用户token
        Map<String, Object> map = new HashMap<>(1 << 2);
        map.put("access_token", token);
        map.put("expires_in", EXPIRE_TIME);
        redisService.setCacheObject(ACCESS_TOKEN + token, loginUserDTO, EXPIRE_TIME, TimeUnit.SECONDS);
        return map;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public Loginuser getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public Loginuser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        Loginuser user = null;
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            user = redisService.getCacheObject(userKey);
        }
        return user;
    }

    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisService.deleteObject(userKey);
        }
    }


    /**
     * 刷新令牌有效期
     *
     * @param loginUserDTO 登录信息
     */
    public void refreshToken(Loginuser loginUserDTO) {
        loginUserDTO.setLoginTime(System.currentTimeMillis());
        loginUserDTO.setExpireTime(loginUserDTO.getLoginTime() + EXPIRE_TIME * MILLIS_SECOND);
        // 根据 uuid 将 loginUserDTO 缓存
        String userKey = getTokenKey(loginUserDTO.getToken());
        redisService.setCacheObject(userKey, loginUserDTO, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }

}
