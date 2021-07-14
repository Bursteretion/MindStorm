package cn.lwjppz.mindstorm.user.service.impl;

import cn.lwjppz.mindstorm.api.education.feign.RemoteEducationFeignService;
import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.core.to.AcademyTo;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.user.model.dto.teacher.TeacherDTO;
import cn.lwjppz.mindstorm.user.model.dto.teacher.TeacherDetailDTO;
import cn.lwjppz.mindstorm.user.model.entity.Teacher;
import cn.lwjppz.mindstorm.user.mapper.TeacherMapper;
import cn.lwjppz.mindstorm.user.model.vo.teacher.TeacherQueryVO;
import cn.lwjppz.mindstorm.user.model.vo.teacher.TeacherVO;
import cn.lwjppz.mindstorm.user.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 教师表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    private final RemoteEducationFeignService remoteEducationFeignService;

    public TeacherServiceImpl(@Lazy RemoteEducationFeignService remoteEducationFeignService) {
        this.remoteEducationFeignService = remoteEducationFeignService;
    }

    @Override
    public List<Teacher> listTeachers() {
        return baseMapper.selectList(getCommonWrapper());
    }

    @Override
    public IPage<TeacherDTO> queryTeachers(TeacherQueryVO teacherQueryVO) {
        LambdaQueryWrapper<Teacher> wrapper = getCommonWrapper();
        if (StringUtils.isNotEmpty(teacherQueryVO.getAcademyId())) {
            wrapper.eq(Teacher::getAcademyId, teacherQueryVO.getAcademyId());
        }
        if (StringUtils.isNotEmpty(teacherQueryVO.getUsername())) {
            wrapper.eq(Teacher::getUsername, teacherQueryVO.getUsername());
        }
        if (StringUtils.isNotEmpty(teacherQueryVO.getRealName())) {
            wrapper.eq(Teacher::getRealName, teacherQueryVO.getRealName());
        }
        if (null != teacherQueryVO.getStartTime() && null != teacherQueryVO.getEndTime()) {
            wrapper.in(Teacher::getGmtCreate, teacherQueryVO.getStartTime(), teacherQueryVO.getEndTime());
        }

        IPage<Teacher> page;
        if (null != teacherQueryVO.getPageIndex() && null != teacherQueryVO.getPageSize()) {
            page = baseMapper.selectPage(new Page<>(teacherQueryVO.getPageIndex(),
                    teacherQueryVO.getPageSize()), wrapper);
        } else {
            page = baseMapper.selectPage(null, wrapper);
        }

        List<Teacher> records = page.getRecords();
        List<TeacherDTO> teachers = convertToTeacherDTO(records);

        IPage<TeacherDTO> resPage = new Page<>();
        BeanUtils.copyProperties(page, resPage);
        resPage.setRecords(teachers);
        return resPage;
    }

    @Override
    public Teacher createTeacher(TeacherVO teacherVO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherVO, teacher);

        baseMapper.insert(teacher);
        return teacher;
    }

    @Override
    public boolean updateTeacher(TeacherVO teacherVO) {
        if (StringUtils.isNotEmpty(teacherVO.getId())) {
            Teacher teacher = new Teacher();
            BeanUtils.copyProperties(teacherVO, teacher);

            return baseMapper.updateById(teacher) > 0;
        }
        return false;
    }

    @Override
    public Teacher infoTeacher(String teacherId) {
        if (StringUtils.isNotEmpty(teacherId)) {
            Teacher teacher = baseMapper.selectById(teacherId);
            if (null == teacher) {
                throw new EntityNotFoundException(ResultStatus.ENTITY_NOT_FOUND);
            }
            return teacher;
        }
        return null;
    }

    @Override
    public boolean batchDeleteTeacher(List<String> teacherIds) {
        return teacherIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteTeacher);
    }

    @Override
    public boolean changeTeacherStatus(String teacherId, Integer status) {
        if (StringUtils.isNotEmpty(teacherId)) {
            Teacher teacher = baseMapper.selectById(teacherId);
            if (null != teacher) {
                teacher.setStatus(status);
                return baseMapper.updateById(teacher) > 0;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTeacher(String teacherId) {
        if (StringUtils.isNotEmpty(teacherId)) {
            return baseMapper.deleteById(teacherId) > 0;
        }
        return false;
    }

    @Override
    public TeacherDTO convertToTeacherDTO(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(teacher, teacherDTO);
        if (StringUtils.isNotEmpty(teacher.getAcademyId())) {
            CommonResult result = remoteEducationFeignService.getAcademyInfo(teacher.getAcademyId());
            AcademyTo academyTo = ServiceUtils.feignValueConvert(result.getData().get("academyTo"), AcademyTo.class);
            if (null != academyTo) {
                teacherDTO.setAcademyName(academyTo.getAcademyName());
            }
        }
        return teacherDTO;
    }

    @Override
    public List<TeacherDTO> convertToTeacherDTO(List<Teacher> teachers) {
        return teachers.stream()
                .map(this::convertToTeacherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDetailDTO convertTeacherDetailDTO(Teacher teacher) {
        TeacherDetailDTO teacherDetailDTO = new TeacherDetailDTO();
        BeanUtils.copyProperties(teacher, teacherDetailDTO);
        return teacherDetailDTO;
    }

    private LambdaQueryWrapper<Teacher> getCommonWrapper() {
        LambdaQueryWrapper<Teacher> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(Teacher::getGmtCreate);
        return queryWrapper;
    }

}
