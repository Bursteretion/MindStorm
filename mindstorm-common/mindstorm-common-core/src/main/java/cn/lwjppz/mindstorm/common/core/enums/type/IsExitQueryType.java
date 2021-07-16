package cn.lwjppz.mindstorm.common.core.enums.type;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-15
 */
public enum IsExitQueryType implements ValueEnum<Integer> {

    /**
     * 查询类型枚举
     */
    ACADEMY(1, "院系"),

    PROFESSION(2, "专业"),

    TEAM(3, "班级");

    private final Integer value;
    private final String name;

    IsExitQueryType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
