package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.questionanswer.QuestionAnswerDTO;
import cn.lwjppz.mindstorm.education.model.entity.QuestionAnswer;
import cn.lwjppz.mindstorm.education.model.vo.questionanswer.QuestionAnswerVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 题目答案表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
public interface QuestionAnswerService extends IService<QuestionAnswer> {

    /**
     * 根据题目Id获取题目答案信息
     *
     * @param questionId 题目Id
     * @return 题目答案信息
     */
    QuestionAnswerDTO getQuestionAnswer(String questionId);

    /**
     * 新增题目答案
     *
     * @param questionAnswerVO 题目答案信息
     * @return 是否新增成功
     */
    boolean createQuestionAnswer(QuestionAnswerVO questionAnswerVO);

    /**
     * 根据题目Id删除题目答案
     *
     * @param questionId 题目Id
     * @return 是否删除成功
     */
    boolean deleteQuestionAnswers(String questionId);

}
