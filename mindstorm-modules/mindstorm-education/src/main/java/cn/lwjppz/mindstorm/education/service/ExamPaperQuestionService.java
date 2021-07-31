package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.exampaperquestion.ExamPaperQuestionDTO;
import cn.lwjppz.mindstorm.education.model.entity.ExamPaperQuestion;
import cn.lwjppz.mindstorm.education.model.vo.exampaperquestion.ExamPaperQuestionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 试卷题目表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
public interface ExamPaperQuestionService extends IService<ExamPaperQuestion> {

    /**
     * 通过试卷Id获取该试卷所有题目
     *
     * @param examPaperId 试卷Id
     * @return 试卷所有题目
     */
    List<ExamPaperQuestionDTO> listExamPaperQuestions(String examPaperId);

    /**
     * 新增试卷题目
     *
     * @param examPaperQuestions 试卷题目列表
     * @return 是否新增成功
     */
    boolean insertExamPaperQuestions(List<ExamPaperQuestionVO> examPaperQuestions);

    /**
     * 删除该试卷所有题目
     *
     * @param examPaperId 试卷Id
     * @return 是否删除成功
     */
    boolean deleteExamPaperQuestions(String examPaperId);

}
