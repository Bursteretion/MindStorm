package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.education.model.entity.Topic;
import cn.lwjppz.mindstorm.education.mapper.TopicMapper;
import cn.lwjppz.mindstorm.education.service.TopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
