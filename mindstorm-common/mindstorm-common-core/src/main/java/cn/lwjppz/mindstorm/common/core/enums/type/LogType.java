package cn.lwjppz.mindstorm.common.core.enums.type;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;

/**
 * <p>
 * 系统日志类型枚举
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-30
 */
public enum LogType implements ValueEnum<Integer> {

    /**
     * 系统日志类型
     */
    OTHER(0, "其他"),

    QUERY(1, "查询"),

    UPDATE(2, "修改"),

    INSERT(3, "新增"),

    DELETE(4, "删除"),

    GRANT(5, "授权"),

    EXPORT(6, "导出"),

    IMPORT(7, "导入"),

    CLEAN(8, "清空数据");

    private final Integer value;
    private final String name;

    LogType(Integer value, String name) {
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
