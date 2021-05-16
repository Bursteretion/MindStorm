package cn.lwjppz.mindstorm.common.core.enums;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-13
 */
public enum PermissionStatus implements ValueEnum<Integer> {

    /**
     * 禁用状态
     */
    FORBIDDEN(0),

    /**
     * 正常状态
     */
    NORMAL(1);

    private final Integer value;

    PermissionStatus(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
