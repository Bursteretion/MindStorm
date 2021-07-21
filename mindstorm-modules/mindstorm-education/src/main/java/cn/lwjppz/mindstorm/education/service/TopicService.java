package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.topic.TopicDTO;
import cn.lwjppz.mindstorm.education.model.dto.topic.TopicSelectDTO;
import cn.lwjppz.mindstorm.education.model.entity.Topic;
import cn.lwjppz.mindstorm.education.model.vo.topic.TopicVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 知识点表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
public interface TopicService extends IService<Topic> {

    /**
     * 获取所有知识点
     *
     * @return 知识点列表
     */
    List<Topic> listTopics();

    /**
     * 新增知识点
     *
     * @param topicVO 知识点信息
     * @return 知识点
     */
    Topic createTopic(TopicVO topicVO);

    /**
     * 根据知识点Id获取知识点信息
     *
     * @param topicId 知识点Id
     * @return 知识点信息
     */
    Topic infoTopic(String topicId);

    /**
     * 更新知识点
     *
     * @param topicVO 知识点信息
     * @return 是否更新成功
     */
    boolean updateTopic(TopicVO topicVO);

    /**
     * 根据知识点Id删除知识点
     *
     * @param topicId 知识点Id
     * @return 是否删除成功
     */
    boolean deleteTopic(String topicId);

    /**
     * 将 Topic 对象转为 TopicDTO 对象
     *
     * @param topic Topic 对象
     * @return TopicDTO 对象
     */
    TopicDTO convertTopicDTO(Topic topic);

    /**
     * 将 Topic 对象集合转为 TopicDTO 对象集合
     *
     * @param topics Topic 对象集合
     * @return TopicDTO 对象集合
     */
    List<TopicDTO> convertTopicDTO(List<Topic> topics);

    /**
     * 将 Topic 对象转为 TopicSelectDTO对象
     *
     * @param topic Topic 对象
     * @return TopicSelectDTO 对象
     */
    TopicSelectDTO convertToTopicSelectDTO(Topic topic);

    /**
     * 将 Topic 对象集合转为 TopicSelectDTO对象集合
     *
     * @param topics Topic 对象集合
     * @return TopicSelectDTO 对象集合
     */
    List<TopicSelectDTO> convertToTopicSelectDTO(List<Topic> topics);

}
