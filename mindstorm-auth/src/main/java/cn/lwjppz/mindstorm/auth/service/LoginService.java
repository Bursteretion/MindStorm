package cn.lwjppz.mindstorm.auth.service;

import cn.lwjppz.mindstorm.auth.feign.UserFeignService;
import cn.lwjppz.mindstorm.common.core.constant.UserConstants;
import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import cn.lwjppz.mindstorm.common.core.enums.UserStatus;
import cn.lwjppz.mindstorm.common.core.exception.LoginException;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.core.utils.SecurityUtils;
import cn.lwjppz.mindstorm.permission.model.dto.LoginUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserFeignService userFeignService;

    public LoginUserDTO login(String username, String password) {
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
        CommonResult result = userFeignService.selectUserByUsername(username);

        if (ResultStatus.ERROR.getCode().equals(result.getCode())) {
            throw new LoginException(result.getMessage());
        }

        if (null == result.getData()) {
            throw new LoginException(ResultStatus.USER_NOT_EXIST);
        }

        // 解决 Feign 远程调用返回 LinkedHashMap 问题
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUserDTO user = objectMapper.convertValue(result.getData().get("user"), LoginUserDTO.class);

        if (UserStatus.DISABLE.getValue().equals(user.getStatus())) {
            throw new LoginException(ResultStatus.USER_IS_DISABLE);
        }

        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            throw new LoginException(ResultStatus.USER_NOT_EXIST);
        }

        return user;
    }

    public void logout(String loginName) {
        // TODO 记录日志
    }

}
