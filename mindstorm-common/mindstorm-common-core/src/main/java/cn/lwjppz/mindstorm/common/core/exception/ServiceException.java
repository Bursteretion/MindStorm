package cn.lwjppz.mindstorm.common.core.exception;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;

/**
 * <p>
 * 业务不存在异常
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
public class ServiceException extends AbstractMindStormException {


    public ServiceException(ResultStatus status) {
        super(status);
    }

    public ServiceException(String msg) {
        super(msg);
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }

}
