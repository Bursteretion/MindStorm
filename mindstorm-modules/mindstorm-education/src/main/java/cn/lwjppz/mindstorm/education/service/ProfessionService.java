package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.profession.ProfessionDTO;
import cn.lwjppz.mindstorm.education.model.entity.Profession;
import cn.lwjppz.mindstorm.education.model.vo.profession.ProfessionQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.profession.ProfessionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 开设专业表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-06-07
 */
public interface ProfessionService extends IService<Profession> {

    /**
     * 获取所有专业信息
     *
     * @return 专业列表
     */
    List<Profession> getProfessions();

    /**
     * 获取所有未禁用的专业信息
     *
     * @return 未禁用专业列表
     */
    List<Profession> getUnDisableProfessions();

    /**
     * 根据院系Id获取该院系开设的所有专业
     *
     * @param academyId 院系Id
     * @return 该院系的开设的所有专业
     */
    List<Profession> getProfessionsByAcademyId(String academyId);

    /**
     * 分页查询专业信息
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 分页信息
     */
    IPage<ProfessionDTO> pageProfessions(int pageNum, int pageSize);

    /**
     * 根据查询条件查询专业信息
     *
     * @param professionQueryVO 专业查询信息
     * @return 分页信息
     */
    IPage<ProfessionDTO> queryProfessions(ProfessionQueryVO professionQueryVO);

    /**
     * 新增专业
     *
     * @param professionVO 专业信息
     * @return 专业信息
     */
    Profession insertProfession(ProfessionVO professionVO);

    /**
     * 更新专业信息
     *
     * @param professionVO 专业信息
     * @return 专业信息
     */
    Profession updateProfession(ProfessionVO professionVO);

    /**
     * 根据专业Id获取专业信息
     *
     * @param professionId 专业Id
     * @return 专业信息
     */
    Profession infoProfession(String professionId);

    /**
     * 改变专业状态
     *
     * @param professionId 专业Id
     * @param status       状态
     * @return 是否更改成功
     */
    boolean changeProfessionStatus(String professionId, Integer status);

    /**
     * 根据专业Id删除该专业
     *
     * @param professionId 专业Id
     * @return 是否删除成功
     */
    boolean deleteProfession(String professionId);

    /**
     * 批量删除专业信息
     *
     * @param professionIds 专业Id集合
     * @return 是否删除成功
     */
    boolean batchDeleteProfession(List<String> professionIds);

    /**
     * 将 Profession 对象转为 ProfessionDTO 对象
     *
     * @param profession Profession 对象
     * @return ProfessionDTO 对象
     */
    ProfessionDTO convertToProfessionDTO(Profession profession);

    /**
     * 将 Profession 集合转为 ProfessionDTO 集合
     *
     * @param professions Profession 集合
     * @return ProfessionDTO 集合
     */
    List<ProfessionDTO> convertToProfessionDTO(List<Profession> professions);

}
