package cn.lwjppz.mindstorm.permission.model.dto.role;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDTO {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleName;

    private String remark;

    private Integer sort;

    private Integer status;

    private Date gmtCreate;
}
