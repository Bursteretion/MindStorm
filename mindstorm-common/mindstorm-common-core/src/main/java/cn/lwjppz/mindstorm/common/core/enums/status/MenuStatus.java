package cn.lwjppz.mindstorm.common.core.enums.status;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-13
 */
public enum MenuStatus implements ValueEnum<Integer> {

    /**
     * 禁用状态
     */
    FORBIDDEN(0),

    /**
     * 正常状态
     */
    NORMAL(1);

    private final Integer value;

    MenuStatus(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
