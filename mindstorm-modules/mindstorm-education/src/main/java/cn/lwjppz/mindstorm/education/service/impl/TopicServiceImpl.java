package cn.lwjppz.mindstorm.education.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.topic.TopicDTO;
import cn.lwjppz.mindstorm.education.model.dto.topic.TopicSelectDTO;
import cn.lwjppz.mindstorm.education.model.entity.Topic;
import cn.lwjppz.mindstorm.education.mapper.TopicMapper;
import cn.lwjppz.mindstorm.education.model.vo.topic.TopicVO;
import cn.lwjppz.mindstorm.education.service.QuestionTopicService;
import cn.lwjppz.mindstorm.education.service.TopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 知识点表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    private final QuestionTopicService questionTopicService;

    public TopicServiceImpl(@Lazy QuestionTopicService questionTopicService) {
        this.questionTopicService = questionTopicService;
    }

    @Override
    public List<Topic> listTopics() {
        return baseMapper.selectList(null);
    }

    @Override
    public Topic createTopic(TopicVO topicVO) {
        var topic = new Topic();
        BeanUtils.copyProperties(topicVO, topic);

        baseMapper.insert(topic);
        return topic;
    }

    @Override
    public Topic infoTopic(String topicId) {
        if (StringUtils.isNotEmpty(topicId)) {
            var topic = baseMapper.selectById(topicId);
            if (null == topic) {
                throw new EntityNotFoundException("没找到知识点Id为：" + topicId + "的知识点");
            }
            return topic;
        }
        return null;
    }

    @Override
    public boolean updateTopic(TopicVO topicVO) {
        var topic = new Topic();
        BeanUtils.copyProperties(topicVO, topic);

        baseMapper.updateById(topic);
        return true;
    }

    @Override
    public boolean deleteTopic(String topicId) {
        if (StringUtils.isNotEmpty(topicId)) {
            baseMapper.deleteById(topicId);
        }
        return true;
    }

    @Override
    public TopicDTO convertTopicDTO(Topic topic) {
        var topicDTO = new TopicDTO();
        BeanUtil.copyProperties(topic, topicDTO);
        return topicDTO;
    }

    @Override
    public List<TopicDTO> convertTopicDTO(List<Topic> topics) {
        return topics.stream()
                .map(this::convertTopicDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TopicSelectDTO convertToTopicSelectDTO(Topic topic) {
        var topicSelectDTO = new TopicSelectDTO();
        BeanUtils.copyProperties(topic, topicSelectDTO);
        topicSelectDTO.setValue(topic.getId());

        var questionCount = questionTopicService.getCountByTopicId(topic.getId());
        topicSelectDTO.setLabel(topic.getName() + " (" + questionCount + ")");
        return topicSelectDTO;
    }

    @Override
    public List<TopicSelectDTO> convertToTopicSelectDTO(List<Topic> topics) {
        return topics.stream()
                .map(this::convertToTopicSelectDTO)
                .collect(Collectors.toList());
    }
}
