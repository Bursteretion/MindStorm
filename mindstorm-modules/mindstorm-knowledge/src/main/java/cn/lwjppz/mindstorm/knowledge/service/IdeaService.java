package cn.lwjppz.mindstorm.knowledge.service;

import cn.lwjppz.mindstorm.knowledge.model.dto.idea.IdeaDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Idea;
import cn.lwjppz.mindstorm.knowledge.model.vo.idea.IdeaVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 知识点 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
public interface IdeaService extends IService<Idea> {

    /**
     * 获取知识点树
     *
     * @return 知识点树
     */
    List<IdeaDTO> getTreeIdeas();

    /**
     * 根据所给的知识点列表生成对应的知识点树
     *
     * @param ideas 知识点列表
     * @return 知识点树
     */
    List<IdeaDTO> getTreeIdeas(List<Idea> ideas);

    /**
     * 根据知识点Id获取知识点信息
     *
     * @param ideaId 知识点Id
     * @return 该Id对应的知识点
     */
    Idea getIdea(String ideaId);

    /**
     * 新增知识点
     *
     * @param ideaVO 知识点信息
     * @return 该知识点
     */
    Idea insertIdea(IdeaVO ideaVO);

    /**
     * 更新知识点信息
     *
     * @param ideaVO 知识点信息
     * @return 知识点信息
     */
    Idea updateIdea(IdeaVO ideaVO);

    /**
     * 根据知识点Id删除该知识点
     *
     * @param ideaId 知识点Id
     * @return 是否删除成功
     */
    boolean deleteIdea(String ideaId);

    /**
     * 批量删除知识点
     *
     * @param ideaIds 知识点Id列表
     * @return 是否删除成功
     */
    boolean batchDeleteIdea(List<String> ideaIds);

}
