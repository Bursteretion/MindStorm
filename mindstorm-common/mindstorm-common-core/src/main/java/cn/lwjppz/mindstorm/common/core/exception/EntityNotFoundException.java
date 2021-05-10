package cn.lwjppz.mindstorm.common.core.exception;

import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;

/**
 * <p>
 * 实体类不存在异常
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
public class EntityNotFoundException extends AbstractMindStormException {


    public EntityNotFoundException(ResultStatus status) {
        super(status);
    }

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }

}
