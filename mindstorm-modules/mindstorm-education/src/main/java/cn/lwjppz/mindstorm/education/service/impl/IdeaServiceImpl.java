package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.mapper.IdeaMapper;
import cn.lwjppz.mindstorm.education.model.dto.idea.IdeaTreeDTO;
import cn.lwjppz.mindstorm.education.model.entity.Idea;
import cn.lwjppz.mindstorm.education.model.vo.ProfessionIdeaVO;
import cn.lwjppz.mindstorm.education.model.vo.idea.IdeaVO;
import cn.lwjppz.mindstorm.education.service.IdeaService;
import cn.lwjppz.mindstorm.education.service.ProfessionIdeaService;
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

    private final ProfessionIdeaService professionIdeaService;

    public IdeaServiceImpl(ProfessionIdeaService professionIdeaService) {
        this.professionIdeaService = professionIdeaService;
    }

    @Override
    public List<IdeaTreeDTO> getTreeIdeas() {
        LambdaQueryWrapper<Idea> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Idea::getSort);
        var ideas = baseMapper.selectList(queryWrapper);
        return generateTreeIdea(ideas);
    }

    @Override
    public List<IdeaTreeDTO> getTreeIdeas(List<Idea> ideas) {
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

        // 新增专业知识点关联
        professionIdeaService.insertProfessionIdea(new ProfessionIdeaVO(ideaVO.getProfessionId(), idea.getId()));

        return idea;
    }

    @Override
    public Idea updateIdea(IdeaVO ideaVO) {
        var idea = new Idea();
        BeanUtils.copyProperties(ideaVO, idea);

        baseMapper.updateById(idea);

        return idea;
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
                    // 删除专业知识点关联
                    professionIdeaService.deleteProfessionIdeaByIdeaId(v.getId());
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
    private List<IdeaTreeDTO> generateTreeIdea(List<Idea> ideas) {
        // 先将Idea集合转为IdeaDTO集合
        List<IdeaTreeDTO> ideaDtoS = convertToIdeaDTO(ideas);

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
        var treeIdeas = new ArrayList<IdeaTreeDTO>();
        ideaDtoS.forEach(v -> {
            if (!StringUtils.isNotEmpty(v.getPid())) {
                treeIdeas.add(v);
            }
        });

        // 通过知识点排序字段升序排序
        treeIdeas.forEach(this::sortChildren);

        return treeIdeas;
    }

    private List<IdeaTreeDTO> convertToIdeaDTO(List<Idea> ideas) {
        return ideas.stream()
                .map(v -> {
                    var ideaDTO = new IdeaTreeDTO();
                    BeanUtils.copyProperties(v, ideaDTO);
                    ideaDTO.setChildren(new ArrayList<>());
                    return ideaDTO;
                })
                .collect(Collectors.toList());
    }

    private Map<String, List<IdeaTreeDTO>> generateIdeaMap(List<IdeaTreeDTO> ideaDtoS) {
        // 建立知识点Id和子知识点集合映射
        var ideaMap = new HashMap<String, List<IdeaTreeDTO>>(ideaDtoS.size());
        ideaDtoS.forEach(v -> ideaMap.put(v.getId(), v.getChildren()));
        return ideaMap;
    }

    private void sortChildren(IdeaTreeDTO ideaDto) {
        if (null != ideaDto.getChildren()) {
            ideaDto.getChildren().sort(Comparator.comparing(IdeaTreeDTO::getSort));
            ideaDto.getChildren().forEach(this::sortChildren);
        }
    }

}
