package cn.lwjppz.mindstorm.common.core.enums;

import cn.lwjppz.mindstorm.common.core.support.IResultStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 枚举结果信息
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-09
 */
@AllArgsConstructor
@Getter
public enum ResultStatus implements IResultStatus {

    /**
     * 枚举结果
     */
    SUCCESS(20000, "操作成功"),

    ERROR(20001, "响应失败！"),

    UPLOAD_FILE_ERROR(20002, "附件上传失败！"),

    FILE_EMPTY(20003, "附件为空！"),

    DELETE_ERROR(20004, "删除失败！"),

    VALID_CODE_SEND_FAIL(20005, "短信验证码发送失败！"),

    LOGIN_ERROR(20006, "登录失败，用户名或密码错误！"),

    REGISTER_ERROR(20007, "注册失败！"),

    USER_EXIT_ERROR(20008, "当前用户已存在！"),

    PARAMS_ERROR(20009, "参数校验失败！"),

    LOGIN_MOBILE_ERROR(20010, "手机号不正确！"),

    LOGIN_PASSWORD_ERROR(20011, "密码不正确！"),

    CODE_ERROR(20012, "验证码不正确！"),

    NOT_LOGIN(20013, "无token，请重新登录！"),

    FETCH_USERINFO_ERROR(20014, "获取用户信息失败！"),

    NOT_FOUND(20015, "未找到改实体"),

    NOT_INSTALL(20016, "博客尚未进行初始化！"),

    BAD_REQUEST(20017, "系统繁忙！"),

    ALREADY_INSTALL_BLOG(20018, "该博客已初始化，不能再次安装！"),

    CATEGORY_EXIST(20019, "该分类已存在！"),

    LINK_EXIST(20020, "该友链已存在"),

    TAG_EXIST(20021, "该标签已存在！"),

    MENU_EXIST(20022, "该菜单已存在"),

    USER_NOT_EXIST(20023, "当前用户不存在/密码错误"),

    USER_IS_DISABLE(20023, "当前用户已被停用"),

    USER_PASSWORD_NO_MATCH(20024, "用户原密码匹配错误"),


    FORBIDDEN(20027, "您无权访问");

    private final Integer code;

    private final String message;
}
