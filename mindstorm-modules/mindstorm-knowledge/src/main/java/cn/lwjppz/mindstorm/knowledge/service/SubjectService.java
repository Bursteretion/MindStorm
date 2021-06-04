package cn.lwjppz.mindstorm.knowledge.service;

import cn.lwjppz.mindstorm.knowledge.model.dto.subject.SubjectDTO;
import cn.lwjppz.mindstorm.knowledge.model.dto.subject.SubjectDetailDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Subject;
import cn.lwjppz.mindstorm.knowledge.model.vo.subject.SubjectVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学科服务类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 获取学科列表
     *
     * @return 学科列表
     */
    List<Subject> getSubjects();

    /**
     * 根据年级获取学科列表
     *
     * @param level 年级
     * @return 该年级对应的学科列表
     */
    List<Subject> getSubjects(Integer level);

    /**
     * 分页查询学科列表
     *
     * @param pageIndex 第几页
     * @param pageSize  每页条数
     * @return 分页信息
     */
    IPage<SubjectDTO> pageBy(int pageIndex, int pageSize);

    /**
     * 新增学科信息
     *
     * @param subjectVO 学科信息
     * @return 学科信息
     */
    Subject insertSubject(SubjectVO subjectVO);

    /**
     * 通过学科Id获取学科信息
     *
     * @param subjectId 学科Id
     * @return 学科信息
     */
    Subject getSubject(String subjectId);

    /**
     * 更新学科信息
     *
     * @param subjectVO 学科信息对象
     * @return 已更新学科信息
     */
    Subject updateSubject(SubjectVO subjectVO);

    /**
     * 删除某学科
     *
     * @param subjectId 学科Id
     * @return 是否删除成功
     */
    boolean deleteSubject(String subjectId);

    /**
     * 批量删除学科
     *
     * @param subjectIds 学科id集合
     * @return @see deleteSubject
     */
    boolean deleteBatchSubjects(List<String> subjectIds);

    /**
     * 将 Subject 对象转为 SubjectDTO 对象
     *
     * @param subject Subject 对象
     * @return SubjectDTO 对象
     */
    SubjectDTO convertToSubjectDTO(Subject subject);

    /**
     * 将 Subject 集合转为 SubjectDTO 集合
     *
     * @param subjects Subject 集合
     * @return SubjectDTO 集合
     */
    List<SubjectDTO> convertToSubjectDTO(List<Subject> subjects);

    /**
     * 将 Subject 对象转为 SubjectDetailDTO 对象
     *
     * @param subject Subject 对象
     * @return SubjectDetailDTO 对象
     */
    SubjectDetailDTO convertToSubjectDetailDTO(Subject subject);

    /**
     * 将 Subject 集合转为 SubjectDetailDTO 集合
     *
     * @param subjects Subject 集合
     * @return SubjectDetailDTO 集合
     */
    List<SubjectDetailDTO> convertToSubjectDetailDTO(List<Subject> subjects);

}
