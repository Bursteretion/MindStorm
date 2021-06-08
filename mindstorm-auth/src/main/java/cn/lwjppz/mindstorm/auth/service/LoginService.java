package cn.lwjppz.mindstorm.auth.service;

import cn.lwjppz.mindstorm.api.permission.feign.RemoteUserFeignService;
import cn.lwjppz.mindstorm.api.permission.model.LoginUser;
import cn.lwjppz.mindstorm.api.system.feign.RemoteLogFeignService;
import cn.lwjppz.mindstorm.common.core.constant.UserConstants;
import cn.lwjppz.mindstorm.common.core.enums.status.LoginStatus;
import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.enums.status.UserStatus;
import cn.lwjppz.mindstorm.common.core.exception.LoginException;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.core.utils.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 登录服务实现
 * </p>
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

    public LoginUser login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            remoteLogFeignService.saveLoginVisit(username, LoginStatus.FAILED.getValue(), "用户/密码必须填写");
            throw new LoginException("用户/密码必须填写");
        }

        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            remoteLogFeignService.saveLoginVisit(username, LoginStatus.FAILED.getValue(), "用户密码不在指定范围");
            throw new LoginException("用户密码不在指定范围");
        }

        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            remoteLogFeignService.saveLoginVisit(username, LoginStatus.FAILED.getValue(), "用户名不在指定范围");
            throw new LoginException("用户名不在指定范围");
        }

        // 查询用户信息
        CommonResult result = remoteUserFeignService.selectUserByUsername(username);

        if (ResultStatus.ERROR.getCode().equals(result.getCode())) {
            throw new LoginException(result.getMessage());
        }

        if (null == result.getData()) {
            remoteLogFeignService.saveLoginVisit(username, LoginStatus.FAILED.getValue(), "登录用户不存在");
            throw new LoginException(ResultStatus.USER_NOT_EXIST);
        }

        // 解决 Feign 远程调用返回 LinkedHashMap 问题
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUser user = objectMapper.convertValue(result.getData().get("user"), LoginUser.class);

        if (UserStatus.DISABLE.getValue().equals(user.getStatus())) {
            remoteLogFeignService.saveLoginVisit(username, LoginStatus.FAILED.getValue(), "用户已停用，请联系管理员");
            throw new LoginException(ResultStatus.USER_IS_DISABLE);
        }

        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            remoteLogFeignService.saveLoginVisit(username, LoginStatus.FAILED.getValue(), "用户密码错误");
            throw new LoginException(ResultStatus.USER_PASSWORD_NO_MATCH);
        }

        remoteLogFeignService.saveLoginVisit(username, LoginStatus.SUCCESS.getValue(), LoginStatus.SUCCESS.getName());
        return user;
    }

    public void logout(String loginName) {
        remoteLogFeignService.saveLoginVisit(loginName, LoginStatus.SUCCESS.getValue(), "退出成功");
    }

}
