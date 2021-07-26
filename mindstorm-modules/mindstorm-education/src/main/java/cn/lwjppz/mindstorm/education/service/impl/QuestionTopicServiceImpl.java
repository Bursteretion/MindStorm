package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.questiontopic.QuestionTopicDTO;
import cn.lwjppz.mindstorm.education.model.entity.QuestionTopic;
import cn.lwjppz.mindstorm.education.mapper.QuestionTopicMapper;
import cn.lwjppz.mindstorm.education.model.vo.questiontopic.QuestionTopicVO;
import cn.lwjppz.mindstorm.education.model.vo.questiontype.QuestionTypeVO;
import cn.lwjppz.mindstorm.education.service.QuestionTopicService;
import cn.lwjppz.mindstorm.education.service.TopicService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 题目知识点关联表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Service
public class QuestionTopicServiceImpl extends ServiceImpl<QuestionTopicMapper, QuestionTopic> implements QuestionTopicService {

    private final TopicService topicService;

    public QuestionTopicServiceImpl(@Lazy TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public List<QuestionTopic> listQuestionTopic(String questionId) {
        LambdaQueryWrapper<QuestionTopic> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(QuestionTopic::getQuestionId, questionId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<QuestionTopic> listQuestionTopicByTopicId(String topicId) {
        if (StringUtils.isNotEmpty(topicId)) {
            LambdaQueryWrapper<QuestionTopic> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionTopic::getTopicId, topicId);
            return baseMapper.selectList(wrapper);
        }
        return null;
    }

    @Override
    public boolean createQuestionTopic(QuestionTopicVO questionTopicVO) {
        // 先删除相关联知识点
        var questionId = questionTopicVO.getQuestionId();
        deleteQuestionTopics(questionId);

        // 新增关联知识点
        var topicIds = questionTopicVO.getTopicIds();
        if (!CollectionUtils.isEmpty(topicIds)) {
            topicIds.forEach(topicId -> {
                var questionTopic = new QuestionTopic();
                questionTopic.setQuestionId(questionId);
                questionTopic.setTopicId(topicId);
                baseMapper.insert(questionTopic);
            });
        }

        return true;
    }

    @Override
    public boolean deleteQuestionTopics(String questionId) {
        if (StringUtils.isNotEmpty(questionId)) {
            LambdaQueryWrapper<QuestionTopic> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionTopic::getQuestionId, questionId);
            baseMapper.delete(wrapper);
        }
        return true;
    }

    @Override
    public Integer getCountByTopicId(String topicId) {
        if (StringUtils.isNotEmpty(topicId)) {
            LambdaQueryWrapper<QuestionTopic> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(QuestionTopic::getTopicId, topicId);
            return baseMapper.selectCount(wrapper);
        }
        return null;
    }

    @Override
    public QuestionTopicDTO convertQuestionTopicDTO(QuestionTopic questionTopic) {
        var questionTopicDTO = new QuestionTopicDTO();

        var topic = topicService.infoTopic(questionTopic.getTopicId());
        questionTopicDTO.setTopicName(topic.getName());

        questionTopicDTO.setQuestionId(questionTopic.getQuestionId());
        return questionTopicDTO;
    }

    @Override
    public List<QuestionTopicDTO> convertQuestionTopicDTO(List<QuestionTopic> questionTopics) {
        return questionTopics.stream()
                .map(this::convertQuestionTopicDTO)
                .collect(Collectors.toList());
    }
}
