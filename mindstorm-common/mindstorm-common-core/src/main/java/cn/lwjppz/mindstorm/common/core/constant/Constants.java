package cn.lwjppz.mindstorm.common.core.constant;

import java.io.File;

/**
 * <p>
 * 通用常量信息
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-15
 */
public class Constants {

    private Constants() {
    }

    /**
     * 用户主目录
     */
    public final static String USER_HOME = System.getProperties().getProperty("user.home");

    public final static String URL_SEPARATOR = "/";

    /**
     * 路径分隔符
     */
    public final static String FILE_SEPARATOR = File.separator;

    /**
     * 上传子目录
     */
    public final static String UPLOAD_SUB_DIR = "/upload/";


    public final static String FILE_PROTOCOL = "file:";

    /**
     * UTF-8
     */
    public static final String UTF8 = "UTF-8";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 令牌有效期（分钟）
     */
    public static final long TOKEN_EXPIRE = 720;
}
