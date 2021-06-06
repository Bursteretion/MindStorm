package cn.lwjppz.mindstorm.knowledge.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.knowledge.model.dto.idea.IdeaDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Idea;
import cn.lwjppz.mindstorm.knowledge.model.entity.SubjectIdea;
import cn.lwjppz.mindstorm.knowledge.mapper.SubjectIdeaMapper;
import cn.lwjppz.mindstorm.knowledge.model.vo.SubjectIdeaVO;
import cn.lwjppz.mindstorm.knowledge.service.IdeaService;
import cn.lwjppz.mindstorm.knowledge.service.SubjectIdeaService;
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
 * 学科知识点服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@Service
public class SubjectIdeaServiceImpl extends ServiceImpl<SubjectIdeaMapper, SubjectIdea> implements SubjectIdeaService {

    private final IdeaService ideaService;

    public SubjectIdeaServiceImpl(@Lazy IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @Override
    public List<IdeaDTO> getTreeIdeas(String subjectId) {
        // 获取当前学科Id对应的所有知识点
        LambdaQueryWrapper<SubjectIdea> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SubjectIdea::getSubjectId, subjectId);
        var subjectIdeas = baseMapper.selectList(queryWrapper);

        // 提取知识点Id集合
        var ideaIds = ServiceUtils.fetchProperty(subjectIdeas, SubjectIdea::getIdeaId);

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
    public boolean insertSubjectIdea(SubjectIdeaVO subjectIdeaVO) {
        var ideaIds = subjectIdeaVO.getIdeaIds();
        if (!CollectionUtils.isEmpty(ideaIds) && !ObjectUtils.isEmpty(ideaIds)) {
            // 新增学科知识点关联之前先将之前的关联删除
            deleteSubjectIdeaBySubjectId(subjectIdeaVO.getSubjectId());
            ideaIds.forEach(v -> {
                // 新增关联
                var subjectIdea = new SubjectIdea();
                subjectIdea.setIdeaId(v);
                subjectIdea.setSubjectId(subjectIdeaVO.getSubjectId());
                baseMapper.insert(subjectIdea);
            });
        }

        return true;
    }

    @Override
    public boolean deleteSubjectIdeaByIdeaId(String ideaId) {
        if (StringUtils.isNotEmpty(ideaId)) {
            LambdaQueryWrapper<SubjectIdea> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SubjectIdea::getIdeaId, ideaId);
            baseMapper.delete(queryWrapper);
        }
        return true;
    }

    @Override
    public boolean deleteSubjectIdeaBySubjectId(String subjectId) {
        if (StringUtils.isNotEmpty(subjectId)) {
            LambdaQueryWrapper<SubjectIdea> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SubjectIdea::getSubjectId, subjectId);
            baseMapper.delete(queryWrapper);
        }
        return true;
    }
}
