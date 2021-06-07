package cn.lwjppz.mindstorm.knowledge.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.knowledge.mapper.IdeaMapper;
import cn.lwjppz.mindstorm.knowledge.model.dto.idea.IdeaDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Idea;
import cn.lwjppz.mindstorm.knowledge.model.vo.idea.IdeaVO;
import cn.lwjppz.mindstorm.knowledge.service.IdeaService;
import cn.lwjppz.mindstorm.knowledge.service.SubjectIdeaService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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

    private final SubjectIdeaService subjectIdeaService;

    public IdeaServiceImpl(SubjectIdeaService subjectIdeaService) {
        this.subjectIdeaService = subjectIdeaService;
    }

    @Override
    public List<IdeaDTO> getTreeIdeas() {
        LambdaQueryWrapper<Idea> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Idea::getSort);
        var ideas = baseMapper.selectList(queryWrapper);
        return generateTreeIdea(ideas);
    }

    @Override
    public List<IdeaDTO> getTreeIdeas(List<Idea> ideas) {
        return generateTreeIdea(ideas);
    }

    @Override
    public Idea getIdea(String ideaId) {
        if (StringUtils.isNotEmpty(ideaId)) {
            return baseMapper.selectById(ideaId);
        }
        return null;
    }

    @Override
    public List<Idea> getIdeaByPid(String pid) {
        if (StringUtils.isNotEmpty(pid)) {
            LambdaQueryWrapper<Idea> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(Idea::getPid, pid);
            return baseMapper.selectList(queryWrapper);
        }
        return new ArrayList<>();
    }

    @Override
    public Idea insertIdea(IdeaVO ideaVO) {
        var idea = new Idea();
        BeanUtils.copyProperties(ideaVO, idea);

        // 新增知识点
        baseMapper.insert(idea);

        return getIdea(idea.getId());
    }

    @Override
    public Idea updateIdea(IdeaVO ideaVO) {
        var idea = new Idea();
        BeanUtils.copyProperties(ideaVO, idea);

        baseMapper.updateById(idea);

        return getIdea(idea.getId());
    }

    @Override
    public boolean deleteIdea(String ideaId) {
        if (StringUtils.isNotEmpty(ideaId)) {
            // 删除知识点
            LambdaQueryWrapper<Idea> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(Idea::getId, ideaId).or().eq(Idea::getPid, ideaId);
            var ideas = baseMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(ideas)) {
               ideas.forEach(v -> {
                   // 删除知识点
                   baseMapper.deleteById(v.getId());
                   // 删除学科知识点关联
                   subjectIdeaService.deleteSubjectIdeaByIdeaId(v.getId());
                   // 递归删除子知识点
                   deleteIdea(v.getId());
               });
            }
        }
        return true;
    }

    @Override
    public boolean batchDeleteIdea(List<String> ideaIds) {
        return ideaIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteIdea);
    }

    /**
     * 生成知识点树
     *
     * @param ideas 知识点列表
     * @return 知识点树
     */
    private List<IdeaDTO> generateTreeIdea(List<Idea> ideas) {
        // 先将Idea集合转为IdeaDTO集合
        List<IdeaDTO> ideaDtoS = convertToIdeaDTO(ideas);

        // 建立知识点Id和子知识点集合映射
        var ideaMap = generateIdeaMap(ideaDtoS);

        ideaDtoS.forEach(v -> {
            // 若当前知识点的父Id不为空
            if (StringUtils.isNotEmpty(v.getPid())) {
                // 从映射中获取该知识点父Id的子知识点列表
                var children = ideaMap.get(v.getPid());
                // 加入其列表
                children.add(v);
                // 替换原来的列表
                ideaMap.put(v.getPid(), children);
            }
        });

        // 筛选出没有父Id的知识点作为根节点
        var treeIdeas = new ArrayList<IdeaDTO>();
        ideaDtoS.forEach(v -> {
            if (!StringUtils.isNotEmpty(v.getPid())) {
                treeIdeas.add(v);
            }
        });

        // 通过知识点排序字段升序排序
        treeIdeas.forEach(this::sortChildren);

        return treeIdeas;
    }

    private List<IdeaDTO> convertToIdeaDTO(List<Idea> ideas) {
        return ideas.stream()
                .map(v -> {
                    var ideaDTO = new IdeaDTO();
                    BeanUtils.copyProperties(v, ideaDTO);
                    ideaDTO.setChildren(new ArrayList<>());
                    return ideaDTO;
                })
                .collect(Collectors.toList());
    }

    private Map<String, List<IdeaDTO>> generateIdeaMap(List<IdeaDTO> ideaDtoS) {
        // 建立知识点Id和子知识点集合映射
        var ideaMap = new HashMap<String, List<IdeaDTO>>(ideaDtoS.size());
        ideaDtoS.forEach(v -> ideaMap.put(v.getId(), v.getChildren()));
        return ideaMap;
    }

    private void sortChildren(IdeaDTO ideaDto) {
        if (null != ideaDto.getChildren()) {
            ideaDto.getChildren().sort(Comparator.comparing(IdeaDTO::getSort));
            ideaDto.getChildren().forEach(this::sortChildren);
        }
    }

}
