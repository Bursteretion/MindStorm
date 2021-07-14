package cn.lwjppz.mindstorm.user.model.dto.teacher;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 教师表
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String academyId;

    private String username;

    private String nickName;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private String phone;

    private String email;

    private Integer status;

    private String avatar;

}
