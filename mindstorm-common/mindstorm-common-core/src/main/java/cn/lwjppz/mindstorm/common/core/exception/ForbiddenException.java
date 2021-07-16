package cn.lwjppz.mindstorm.common.core.exception;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;

/**
 * Forbidden exception.
 *
 * @author : lwj
 * @since : 2020-08-21
 */
public class ForbiddenException extends AbstractMindStormException {

    public ForbiddenException(ResultStatus status) {
        super(status);
    }

    public ForbiddenException(String msg) {
        super(msg);
    }

    @Override
    public ResultStatus getStatus() {
        return ResultStatus.FORBIDDEN;
    }

}
