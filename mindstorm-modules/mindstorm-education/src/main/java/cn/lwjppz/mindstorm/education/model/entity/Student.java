package cn.lwjppz.mindstorm.education.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mse_student")
@ApiModel(value = "Student对象", description = "学生表")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "院系Id")
    private String academyId;

    @ApiModelProperty(value = "专业Id")
    private String professionId;

    @ApiModelProperty(value = "班级Id")
    private String teamId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "学生学号")
    private String sno;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别 1.男 2.女")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    private Date birthDay;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态(0:禁止,1:正常)")
    private Integer status;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @ApiModelProperty(value = "微信openId")
    private String wxOpenId;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
