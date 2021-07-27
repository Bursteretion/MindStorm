package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.question.QuestionDTO;
import cn.lwjppz.mindstorm.education.model.dto.question.QuestionDetailDTO;
import cn.lwjppz.mindstorm.education.model.entity.Question;
import cn.lwjppz.mindstorm.education.model.vo.question.QuestionQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.question.QuestionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题目表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
public interface QuestionService extends IService<Question> {

    /**
     * 通过题型Id获取该题型共有多少题
     *
     * @param questionTypeId 题型Id
     * @return 题目数量
     */
    Integer getCountByQuestionTypeId(String questionTypeId);

    /**
     * 条件查询题目
     *
     * @param questionQueryVO 查询条件
     * @return 题目分页信息
     */
    IPage<QuestionDTO> listQuestions(QuestionQueryVO questionQueryVO);

    /**
     * 新增题目
     *
     * @param questionVO 题目信息
     * @return 是否新增成功
     */
    boolean createQuestion(QuestionVO questionVO);

    /**
     * 根据题目Id获取题目信息
     *
     * @param questionId 题目Id
     * @return 题目信息
     */
    Question infoQuestion(String questionId);

    /**
     * 更新题目信息
     *
     * @param questionVO 题目信息
     * @return 是否更新成功
     */
    boolean updateQuestion(QuestionVO questionVO);

    /**
     * 删除题目
     *
     * @param questionId 题目Id
     * @return 是否删除成功
     */
    boolean deleteQuestion(String questionId);

    /**
     * 将 Question 对象转为 QuestionDTO对象
     *
     * @param question Question 对象
     * @return QuestionDTO对象
     */
    QuestionDTO convertToQuestionDTO(Question question);

    /**
     * 将 Question 对象集合转为 QuestionDTO对象集合
     *
     * @param questions Question 对象集合
     * @return QuestionDTO对象集合
     */
    List<QuestionDTO> convertToQuestionDTO(List<Question> questions);

    /**
     * 将 Question 对象转为 QuestionDetailDTO 对象
     *
     * @param question Question 对象
     * @return QuestionDetailDTO 对象
     */
    QuestionDetailDTO convertToQuestionDetailDTO(Question question);

}
