package cn.lwjppz.mindstorm.file.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 课程封面图片
 * </p>
 *
 * @author lwjppz
 * @since 2021-07-15
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("msf_course_cover")
@ApiModel(value = "CourseCover对象", description = "课程封面图片表")
public class CourseCover implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "附件名")
    private String name;

    @ApiModelProperty(value = "文件 key")
    private String fileKey;

    @ApiModelProperty(value = "附件存储位置类型")
    private Integer storageType;

    @ApiModelProperty(value = "附件类型")
    private String mediaType;

    @ApiModelProperty(value = "宽")
    private Integer width;

    @ApiModelProperty(value = "高")
    private Integer height;

    @ApiModelProperty(value = "大小")
    private Long size;

    @ApiModelProperty(value = "文件后缀（jpg、png...）")
    private String suffix;

    @ApiModelProperty(value = "地址")
    private String path;

    @ApiModelProperty("缩略图地址")
    private String thumbPath;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
