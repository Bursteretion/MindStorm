package cn.lwjppz.mindstorm.common.core.support;

/**
 * <p>
 * 附件存储位置类型
 * </p>
 *
 * @author : lwj
 * @since : 2020-08-18
 */
public enum StorageType implements ValueEnum<Integer> {

    /**
     * 本地服务器
     */
    LOCAL(1, "本地");

    private final Integer value;
    private final String name;

    StorageType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Get enum value.
     *
     * @return enum value
     */
    @Override
    public Integer getValue() {
        return value;
    }

}
