package cn.lwjppz.mindstorm.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.File;

/**
 * @author : lwj
 * @since : 2020-08-18
 */
public final class FileNameUtils {

    private FileNameUtils() {
    }

    /**
     * 获取文件的基本名称 <br>
     * 例如: <br>
     * 文件名: /home/test/test.txt <br>
     * 基本名: test
     *
     * @param filename 文件名
     * @return 文件的基本名称
     */
    @NonNull
    public static String getBasename(@NonNull String filename) {
        Assert.hasText(filename, "文件名不能为空！");

        // 找到最后一个斜杠所在位置
        int separatorLastIndex = StringUtils.lastIndexOf(filename, File.separatorChar);

        if (separatorLastIndex == filename.length() - 1) {
            return StringUtils.EMPTY;
        }

        if (separatorLastIndex >= 0 && separatorLastIndex < filename.length() - 1) {
            filename = filename.substring(separatorLastIndex + 1);
        }

        // 查找最后一个点所在位置
        int dotLastIndex = StringUtils.lastIndexOf(filename, '.');

        if (dotLastIndex < 0) {
            return filename;
        }

        return filename.substring(0, dotLastIndex);
    }

    /**
     * 获取文件的扩展名 <br>
     * <code>
     * 例如: <br>
     * 文件名: /home/test/test.txt <br>
     * 扩展名: txt
     * </code>
     *
     * @param filename 文件名
     * @return 扩展名
     */
    @NonNull
    public static String getExtension(@NonNull String filename) {
        Assert.hasText(filename, "Filename must not be blank");

        // 查找最后一个点所在位置
        int dotLastIndex = StringUtils.lastIndexOf(filename, '.');

        if (dotLastIndex < 0) {
            return StringUtils.EMPTY;
        }

        return filename.substring(dotLastIndex + 1);
    }

}

