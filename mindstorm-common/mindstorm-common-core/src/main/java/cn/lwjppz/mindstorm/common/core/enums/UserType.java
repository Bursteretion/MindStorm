package cn.lwjppz.mindstorm.common.core.enums;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p>
 * 用户类型枚举
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
public enum UserType implements ValueEnum<Integer> {

    /**
     * 学生
     */
    STUDENT(1, "学生"),

    /**
     * 教师
     */
    TEACHER(2, "教师"),

    /**
     * 管理员
     */
    ADMIN(3, "管理员");

    private final Integer value;
    private final String name;

    UserType(Integer value, String name) {
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
