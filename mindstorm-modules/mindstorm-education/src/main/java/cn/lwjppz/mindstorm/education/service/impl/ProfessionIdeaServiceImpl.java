package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.mapper.ProfessionIdeaMapper;
import cn.lwjppz.mindstorm.education.model.dto.idea.IdeaDTO;
import cn.lwjppz.mindstorm.education.model.entity.Idea;
import cn.lwjppz.mindstorm.education.model.entity.ProfessionIdea;
import cn.lwjppz.mindstorm.education.model.vo.ProfessionIdeaVO;
import cn.lwjppz.mindstorm.education.service.IdeaService;
import cn.lwjppz.mindstorm.education.service.ProfessionIdeaService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 专业知识点服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@Service
public class ProfessionIdeaServiceImpl extends ServiceImpl<ProfessionIdeaMapper, ProfessionIdea> implements ProfessionIdeaService {

    private final IdeaService ideaService;

    public ProfessionIdeaServiceImpl(@Lazy IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @Override
    public List<IdeaDTO> getTreeIdeas(String professionId) {
        // 获取当前专业Id对应的所有知识点
        LambdaQueryWrapper<ProfessionIdea> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProfessionIdea::getProfessionId, professionId);
        var professionIdeas = baseMapper.selectList(queryWrapper);

        // 提取知识点Id集合
        var ideaIds = ServiceUtils.fetchProperty(professionIdeas, ProfessionIdea::getIdeaId);

        // 根据知识点Id集合获取对应的知识点列表
        var ideas = new HashSet<Idea>();
        ideaIds.forEach(v -> {
            ideas.add(ideaService.getIdea(v));
            ideas.addAll(ideaService.getIdeaByPid(v));
        });
        var ideasList = new ArrayList<>(ideas);

        return ideaService.getTreeIdeas(ideasList);
    }

    @Override
    public boolean insertProfessionIdea(ProfessionIdeaVO professionIdeaVO) {
        var ideaIds = professionIdeaVO.getIdeaIds();
        if (!CollectionUtils.isEmpty(ideaIds) && !ObjectUtils.isEmpty(ideaIds)) {
            // 新增专业知识点关联之前先将之前的关联删除
            deleteProfessionIdeaByProfessionId(professionIdeaVO.getProfessionId());
            ideaIds.forEach(v -> {
                // 新增关联
                var subjectIdea = new ProfessionIdea();
                subjectIdea.setIdeaId(v);
                subjectIdea.setProfessionId(professionIdeaVO.getProfessionId());
                baseMapper.insert(subjectIdea);
            });
        }

        return true;
    }

    @Override
    public boolean deleteProfessionIdeaByIdeaId(String ideaId) {
        if (StringUtils.isNotEmpty(ideaId)) {
            LambdaQueryWrapper<ProfessionIdea> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(ProfessionIdea::getIdeaId, ideaId);
            baseMapper.delete(queryWrapper);
        }
        return true;
    }

    @Override
    public boolean deleteProfessionIdeaByProfessionId(String professionId) {
        if (StringUtils.isNotEmpty(professionId)) {
            LambdaQueryWrapper<ProfessionIdea> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(ProfessionIdea::getProfessionId, professionId);
            baseMapper.delete(queryWrapper);
        }
        return true;
    }
}
