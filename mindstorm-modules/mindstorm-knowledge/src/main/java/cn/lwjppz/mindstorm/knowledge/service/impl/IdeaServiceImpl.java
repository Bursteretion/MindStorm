package cn.lwjppz.mindstorm.knowledge.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.knowledge.model.dto.idea.IdeaDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Idea;
import cn.lwjppz.mindstorm.knowledge.mapper.IdeaMapper;
import cn.lwjppz.mindstorm.knowledge.model.vo.idea.IdeaVO;
import cn.lwjppz.mindstorm.knowledge.service.IdeaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 知识点服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@Service
public class IdeaServiceImpl extends ServiceImpl<IdeaMapper, Idea> implements IdeaService {

    @Override
    public List<IdeaDTO> getIdeas() {
        return null;
    }

    @Override
    public Idea getIdea(String ideaId) {
        return null;
    }

    @Override
    public IdeaDTO getIdeaByPid(String ideaPid) {
        return null;
    }

    @Override
    public Idea insertIdea(IdeaVO ideaVO) {
        return null;
    }

    @Override
    public Idea updateIdea(IdeaVO ideaVO) {
        return null;
    }

    @Override
    public boolean deleteIdea(String ideaId) {
        if (StringUtils.isNotEmpty(ideaId)) {
            Idea idea = getIdea(ideaId);
            if (null == idea) {
                throw new EntityNotFoundException(ResultStatus.NOT_FOUND);
            }
            baseMapper.deleteById(ideaId);
        }
        return true;
    }

    @Override
    public boolean batchDeleteIdea(List<String> ideaIds) {
        return ideaIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteIdea);
    }
}
