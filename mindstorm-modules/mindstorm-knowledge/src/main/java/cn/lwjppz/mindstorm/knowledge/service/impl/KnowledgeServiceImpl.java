package cn.lwjppz.mindstorm.knowledge.service.impl;

import cn.lwjppz.mindstorm.knowledge.model.entity.Knowledge;
import cn.lwjppz.mindstorm.knowledge.mapper.KnowledgeMapper;
import cn.lwjppz.mindstorm.knowledge.service.KnowledgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 知识点服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

}
