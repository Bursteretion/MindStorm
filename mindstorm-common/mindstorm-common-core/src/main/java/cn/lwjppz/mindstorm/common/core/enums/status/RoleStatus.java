package cn.lwjppz.mindstorm.common.core.enums.status;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p>
 * 角色状态枚举
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-16
 */
public enum RoleStatus implements ValueEnum<Integer> {

    /**
     * 正常状态
     */
    NORMAL(1, "正常"),

    /**
     * 禁用状态
     */
    DISABLE(0, "禁用");

    private final Integer value;
    private final String name;

    RoleStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
