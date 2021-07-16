package cn.lwjppz.mindstorm.common.core.exception;


import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;

/**
 * File operation exception.
 *
 * @author : lwj
 * @since : 2020-08-18
 */
public class FileOperationException extends AbstractMindStormException {

    public FileOperationException(ResultStatus status) {
        super(status);
    }

    public FileOperationException(String msg) {
        super(msg);
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }

}
