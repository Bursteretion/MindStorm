package cn.lwjppz.mindstorm.auth.service;

import cn.lwjppz.mindstorm.api.permission.feign.RemoteUserFeignService;
import cn.lwjppz.mindstorm.api.permission.model.Loginuser;
import cn.lwjppz.mindstorm.api.system.feign.RemoteLogFeignService;
import cn.lwjppz.mindstorm.common.core.constant.UserConstants;
import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import cn.lwjppz.mindstorm.common.core.enums.UserStatus;
import cn.lwjppz.mindstorm.common.core.exception.LoginException;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.core.utils.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
@Service
public class LoginService {

    private final RemoteUserFeignService remoteUserFeignService;
    private final RemoteLogFeignService remoteLogFeignService;

    public LoginService(RemoteUserFeignService remoteUserFeignService,
                        RemoteLogFeignService remoteLogFeignService) {
        this.remoteUserFeignService = remoteUserFeignService;
        this.remoteLogFeignService = remoteLogFeignService;
    }

    public Loginuser login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            // TODO log
            throw new LoginException("用户/密码必须填写");
        }

        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new LoginException("用户密码不在指定范围");
        }

        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new LoginException("用户名不在指定范围");
        }

        // 查询用户信息
        CommonResult result = remoteUserFeignService.selectUserByUsername(username);

        if (ResultStatus.ERROR.getCode().equals(result.getCode())) {
            throw new LoginException(result.getMessage());
        }

        if (null == result.getData()) {
            throw new LoginException(ResultStatus.USER_NOT_EXIST);
        }

        // 解决 Feign 远程调用返回 LinkedHashMap 问题
        ObjectMapper objectMapper = new ObjectMapper();
        Loginuser user = objectMapper.convertValue(result.getData().get("user"), Loginuser.class);

        if (UserStatus.DISABLE.getValue().equals(user.getStatus())) {
            throw new LoginException(ResultStatus.USER_IS_DISABLE);
        }

        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            throw new LoginException(ResultStatus.USER_PASSWORD_NO_MATCH);
        }

        return user;
    }

    public void logout(String loginName) {
        // TODO 记录日志
    }

}
