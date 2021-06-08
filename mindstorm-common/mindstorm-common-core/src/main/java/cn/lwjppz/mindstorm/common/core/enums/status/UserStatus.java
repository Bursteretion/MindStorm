package cn.lwjppz.mindstorm.common.core.enums.status;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
public enum UserStatus implements ValueEnum<Integer> {

    /**
     * 正常状态
     */
    NORMAL(1, "正常"),

    /**
     * 禁止状态
     */
    DISABLE(0, "禁止");

    private final Integer value;
    private final String name;

    UserStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
