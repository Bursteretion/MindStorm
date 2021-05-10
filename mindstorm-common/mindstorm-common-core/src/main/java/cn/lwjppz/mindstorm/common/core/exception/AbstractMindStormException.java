package cn.lwjppz.mindstorm.common.core.exception;

import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import lombok.Getter;

/**
 * <p>
 * 全局异常基类
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
@Getter
public abstract class AbstractMindStormException extends RuntimeException {

    public final ResultStatus status;

    protected AbstractMindStormException(ResultStatus status) {
        super(status.getMessage());
        this.status = status;
    }

    protected AbstractMindStormException(String msg) {
        super(msg);
        this.status = ResultStatus.ERROR;
    }

    /**
     * Get exception status
     *
     * @return exception status
     */
    public abstract ResultStatus getStatus();

}
