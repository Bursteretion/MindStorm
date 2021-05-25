package cn.lwjppz.mindstorm.common.core.exception;

import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;

/**
 * <p>
 * 权限异常
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
public class PreAuthorizeException extends AbstractMindStormException {

    public PreAuthorizeException(ResultStatus status) {
        super(status);
    }

    public PreAuthorizeException(String msg) {
        super(msg);
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }
}
