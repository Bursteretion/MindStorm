package cn.lwjppz.mindstorm.common.core.enums.type;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p>
 * 图片所属类型枚举
 * </p>
 *
 * @author : lwj
 * @since : 2021-07-16
 */
public enum ImageSourceType implements ValueEnum<Integer> {

    /**
     * 课程封面图片
     */
    COURSE_COVER(1, "course/course-cover");


    private final Integer value;
    private final String name;

    ImageSourceType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
