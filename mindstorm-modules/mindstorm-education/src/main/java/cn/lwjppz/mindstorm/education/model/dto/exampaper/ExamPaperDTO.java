package cn.lwjppz.mindstorm.education.model.dto.exampaper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamPaperDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 试卷Id
     */
    private String id;

    /**
     * 上级文件夹Id
     */
    private String pid;

    /**
     * 试卷（文件夹）名
     */
    private String title;

    /**
     * 试卷题目数量
     */
    private Integer questionCount;

    /**
     * 试卷题目数量
     */
    private Integer publishCount;

    /**
     * 试卷难度（0-简单，1-中等，2-困难）
     */
    private Integer difficulty;

    /**
     * 是否是文件夹
     */
    private Boolean isFolder;

    /**
     * 创建用户真实姓名
     */
    private String userRealName;

    /**
     * 创建时间
     */
    private Date gmtCreate;

}
