package cn.lwjppz.mindstorm.common.core.exception;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;

/**
 * Entity already exist exception
 *
 * @author : lwj
 * @since : 2021-05-10
 */
public class AlreadyExistsException extends AbstractMindStormException {

    public AlreadyExistsException(ResultStatus status) {
        super(status);
    }

    public AlreadyExistsException(String msg) {
        super(msg);
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }

}
