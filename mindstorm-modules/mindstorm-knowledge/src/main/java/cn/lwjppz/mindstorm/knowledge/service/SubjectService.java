package cn.lwjppz.mindstorm.knowledge.service;

import cn.lwjppz.mindstorm.knowledge.model.dto.subject.SubjectDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Subject;
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



}
