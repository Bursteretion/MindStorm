package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.idea.IdeaTreeDTO;
import cn.lwjppz.mindstorm.education.model.entity.ProfessionIdea;
import cn.lwjppz.mindstorm.education.model.vo.ProfessionIdeaVO;
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
public interface ProfessionIdeaService extends IService<ProfessionIdea> {

    /**
     * 根据学科Id获取该学科的知识点树
     *
     * @param professionId 学科Id
     * @return 该学科知识点树
     */
    List<IdeaTreeDTO> getTreeIdeas(String professionId);

    /**
     * 新增学科知识点关联
     *
     * @param professionIdeaVO 学科知识点关联信息
     * @return 是否新增关联成功
     */
    boolean insertProfessionIdea(ProfessionIdeaVO professionIdeaVO);

    /**
     * 根据知识点Id删除学科知识点关联信息
     *
     * @param ideaId 知识点Id
     * @return 是否删除成功
     */
    boolean deleteProfessionIdeaByIdeaId(String ideaId);

    /**
     * 根据学科Id删除学科知识点关联信息
     *
     * @param professionId 学科Id
     * @return 是否删除成功
     */
    boolean deleteProfessionIdeaByProfessionId(String professionId);

}
