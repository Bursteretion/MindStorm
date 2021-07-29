package cn.lwjppz.mindstorm.education.model.vo.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionUploadVO {

    @ApiModelProperty(value = "所属课程Id")
    private String courseId;

    @ApiModelProperty(value = "创建用户Id")
    private String userId;

    @ApiModelProperty(value = "题目导入excel文件")
    private MultipartFile importFile;

}
