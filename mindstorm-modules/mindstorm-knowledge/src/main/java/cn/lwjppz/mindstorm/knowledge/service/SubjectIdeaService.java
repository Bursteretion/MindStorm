package cn.lwjppz.mindstorm.knowledge.service;

import cn.lwjppz.mindstorm.knowledge.model.dto.idea.IdeaDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.SubjectIdea;
import cn.lwjppz.mindstorm.knowledge.model.vo.SubjectIdeaVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学科知识点服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
public interface SubjectIdeaService extends IService<SubjectIdea> {

    /**
     * 根据学科Id获取该学科的知识点树
     *
     * @param subjectId 学科Id
     * @return 该学科知识点树
     */
    List<IdeaDTO> getTreeIdeas(String subjectId);

    /**
     * 新增学科知识点关联
     *
     * @param subjectIdeaVO 学科知识点关联信息
     * @return 是否新增关联成功
     */
    boolean insertSubjectIdea(SubjectIdeaVO subjectIdeaVO);

    /**
     * 根据知识点Id删除学科知识点关联信息
     *
     * @param ideaId 知识点Id
     * @return 是否删除成功
     */
    boolean deleteSubjectIdeaByIdeaId(String ideaId);

    /**
     * 根据学科Id删除学科知识点关联信息
     *
     * @param subjectId 学科Id
     * @return 是否删除成功
     */
    boolean deleteSubjectIdeaBySubjectId(String subjectId);

}
