package cn.lwjppz.mindstorm.education.model.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String academyId;

    private String professionId;

    private String username;

    private String realName;

    private String sno;

    private Integer age;

    private Integer sex;

    private String phone;

    private String email;

    private Integer status;

}
