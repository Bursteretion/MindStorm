package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.entity.AcademyProfession;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学院专业关联表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-06-07
 */
public interface AcademyProfessionService extends IService<AcademyProfession> {

    /**
     * 根据院系Id获取该院系下的所有院系专业关联信息
     *
     * @param academyId 院系Id
     * @return 关联信息集合
     */
    List<AcademyProfession> getAcademyProfessionsByAcademyId(String academyId);

    /**
     * 新增院系专业关联
     *
     * @param academyId    院系Id
     * @param professionId 专业Id
     * @return 院系专业关联信息
     */
    AcademyProfession insertAcademyProfession(String academyId, String professionId);

    /**
     * 根据院系Id删除该院系所关联的所有专业信息
     *
     * @param academyId 院系Id
     * @return 是否删除成功
     */
    boolean deleteAcademyProfessionByAcademyId(String academyId);

    /**
     * 根据专业Id删除院系专业关联信息
     *
     * @param professionId 专业Id
     * @return 是否删除成功
     */
    boolean deleteAcademyProfessionByProfessionId(String professionId);

}
