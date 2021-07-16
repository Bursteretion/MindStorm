package cn.lwjppz.mindstorm.common.core.exception;


import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;

/**
 * Repeat type exception.
 *
 * @author : lwj
 * @since : 2020-08-18
 */
public class RepeatTypeException extends AbstractMindStormException {

    public RepeatTypeException(ResultStatus status) {
        super(status);
    }

    public RepeatTypeException(String msg) {
        super(msg);
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }

}
