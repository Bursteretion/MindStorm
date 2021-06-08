package cn.lwjppz.mindstorm.common.core.exception;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;

/**
 * <p>
 *
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
public class LoginException extends AbstractMindStormException {

    public LoginException(ResultStatus status) {
        super(status);
    }

    public LoginException(String msg) {
        super(msg);
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }
}
