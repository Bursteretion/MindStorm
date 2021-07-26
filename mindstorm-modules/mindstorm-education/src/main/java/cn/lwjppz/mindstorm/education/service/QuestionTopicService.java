package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.questiontopic.QuestionTopicDTO;
import cn.lwjppz.mindstorm.education.model.entity.QuestionTopic;
import cn.lwjppz.mindstorm.education.model.vo.questiontopic.QuestionTopicVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题目知识点关联表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
public interface QuestionTopicService extends IService<QuestionTopic> {

    /**
     * 根据题目Id获取相关联知识点
     *
     * @param questionId 题目Id
     * @return 关联知识点列表
     */
    List<QuestionTopic> listQuestionTopic(String questionId);

    /**
     * 根据知识点Id获取题目列表
     *
     * @param topicId 知识点Id
     * @return 题目列表
     */
    List<QuestionTopic> listQuestionTopicByTopicId(String topicId);

    /**
     * 新增题目知识点关联
     *
     * @param questionTopicVO 题目知识点关联信息
     * @return 是否新增成功
     */
    boolean createQuestionTopic(QuestionTopicVO questionTopicVO);

    /**
     * 根据题目Id删除题目知识点关联
     *
     * @param questionId 题目Id
     * @return 是否删除成功
     */
    boolean deleteQuestionTopics(String questionId);

    /**
     * 根据知识点Id获取关联题数
     *
     * @param topicId 知识点Id
     * @return 关联题数
     */
    Integer getCountByTopicId(String topicId);

    /**
     * 将 QuestionTopic 对象转为 QuestionTopicDTO 对象
     *
     * @param questionTopic QuestionTopic 对象
     * @return QuestionTopicDTO 对象
     */
    QuestionTopicDTO convertQuestionTopicDTO(QuestionTopic questionTopic);

    /**
     * 将 QuestionTopic 对象集合转为 QuestionTopicDTO 对象集合
     *
     * @param questionTopics QuestionTopic 对象集合
     * @return QuestionTopicDTO 对象集合
     */
    List<QuestionTopicDTO> convertQuestionTopicDTO(List<QuestionTopic> questionTopics);

}
