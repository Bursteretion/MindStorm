package cn.lwjppz.mindstorm.user.service.impl;

import cn.lwjppz.mindstorm.api.education.feign.RemoteEducationFeignService;
import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.common.core.to.AcademyTo;
import cn.lwjppz.mindstorm.common.core.to.ProfessionTo;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.user.model.dto.student.StudentDTO;
import cn.lwjppz.mindstorm.user.model.dto.student.StudentDetailDTO;
import cn.lwjppz.mindstorm.user.model.entity.Student;
import cn.lwjppz.mindstorm.user.mapper.StudentMapper;
import cn.lwjppz.mindstorm.user.model.vo.student.StudentQueryVO;
import cn.lwjppz.mindstorm.user.model.vo.student.StudentVO;
import cn.lwjppz.mindstorm.user.service.StudentService;
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
 * 学生表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-13
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private final RemoteEducationFeignService remoteEducationFeignService;

    public StudentServiceImpl(@Lazy RemoteEducationFeignService remoteEducationFeignService) {
        this.remoteEducationFeignService = remoteEducationFeignService;
    }

    @Override
    public List<Student> listStudents() {
        return baseMapper.selectList(getCommonWrapper());
    }

    @Override
    public IPage<StudentDTO> queryStudents(StudentQueryVO studentQueryVO) {
        LambdaQueryWrapper<Student> wrapper = getCommonWrapper();
        if (StringUtils.isNotEmpty(studentQueryVO.getAcademyId())) {
            wrapper.eq(Student::getAcademyId, studentQueryVO.getAcademyId());
        }
        if (StringUtils.isNotEmpty(studentQueryVO.getProfessionId())) {
            wrapper.eq(Student::getProfessionId, studentQueryVO.getProfessionId());
        }
        if (StringUtils.isNotEmpty(studentQueryVO.getUsername())) {
            wrapper.like(Student::getUsername, studentQueryVO.getUsername());
        }
        if (StringUtils.isNotEmpty(studentQueryVO.getRealName())) {
            wrapper.like(Student::getRealName, studentQueryVO.getRealName());
        }
        if (StringUtils.isNotEmpty(studentQueryVO.getSno())) {
            wrapper.like(Student::getSno, studentQueryVO.getSno());
        }
        if (null != studentQueryVO.getSex()) {
            wrapper.eq(Student::getSex, studentQueryVO.getSex());
        }
        if (StringUtils.isNotEmpty(studentQueryVO.getPhone())) {
            wrapper.like(Student::getPhone, studentQueryVO.getPhone());
        }
        if (null != studentQueryVO.getStatus()) {
            wrapper.eq(Student::getStatus, studentQueryVO.getStatus());
        }
        if (null != studentQueryVO.getStartTime() && null != studentQueryVO.getEndTime()) {
            wrapper.in(Student::getGmtCreate, studentQueryVO.getStartTime(), studentQueryVO.getEndTime());
        }

        IPage<Student> page;
        if (null != studentQueryVO.getPageIndex() && null != studentQueryVO.getPageSize()) {
            page = baseMapper.selectPage(new Page<>(studentQueryVO.getPageIndex(),
                    studentQueryVO.getPageSize()), wrapper);
        } else {
            page = baseMapper.selectPage(null, wrapper);
        }

        List<Student> records = page.getRecords();
        List<StudentDTO> students = convertToStudentDTO(records);

        IPage<StudentDTO> resPage = new Page<>();
        BeanUtils.copyProperties(page, resPage);
        resPage.setRecords(students);
        return resPage;
    }

    @Override
    public Student createStudent(StudentVO studentVO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentVO, student);

        baseMapper.insert(student);
        return student;
    }

    @Override
    public boolean updateStudent(StudentVO studentVO) {
        if (StringUtils.isNotEmpty(studentVO.getId())) {
            Student student = new Student();
            BeanUtils.copyProperties(studentVO, student);

            return baseMapper.updateById(student) > 0;
        }
        return false;
    }

    @Override
    public Student infoStudent(String studentId) {
        if (StringUtils.isNotEmpty(studentId)) {
            Student student = baseMapper.selectById(studentId);
            if (null == student) {
                throw new EntityNotFoundException(ResultStatus.ENTITY_NOT_FOUND);
            }
            return student;
        }
        return null;
    }

    @Override
    public boolean batchDeleteStudent(List<String> studentIds) {
        return studentIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteStudent);
    }

    @Override
    public boolean changeStudentStatus(String studentId, Integer status) {
        if (StringUtils.isNotEmpty(studentId)) {
            Student student = baseMapper.selectById(studentId);
            if (null != student) {
                student.setStatus(status);
                return baseMapper.updateById(student) > 0;
            }
        }
        return false;
    }

    @Override
    public boolean deleteStudent(String studentId) {
        if (StringUtils.isNotEmpty(studentId)) {
            return baseMapper.deleteById(studentId) > 0;
        }
        return false;
    }

    @Override
    public StudentDTO convertToStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO);
        if (StringUtils.isNotEmpty(student.getAcademyId())) {
            CommonResult result = remoteEducationFeignService.getAcademyInfo(student.getAcademyId());
            AcademyTo academyTo = ServiceUtils.feignValueConvert(result.getData().get("academyTo"), AcademyTo.class);
            if (null != academyTo) {
                studentDTO.setAcademyName(academyTo.getAcademyName());
            }
        }
        if (StringUtils.isNotEmpty(student.getProfessionId())) {
            CommonResult result = remoteEducationFeignService.getProfessionInfo(student.getProfessionId());
            ProfessionTo professionTo = ServiceUtils.feignValueConvert(result.getData().get("professionTo"),
                    ProfessionTo.class);
            if (null != professionTo) {
                studentDTO.setProfessionName(professionTo.getProfessionName());
            }
        }
        return studentDTO;
    }

    @Override
    public List<StudentDTO> convertToStudentDTO(List<Student> students) {
        return students.stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDetailDTO convertStudentDetailDTO(Student student) {
        StudentDetailDTO studentDetailDTO = new StudentDetailDTO();
        BeanUtils.copyProperties(student, studentDetailDTO);
        return studentDetailDTO;
    }

    private LambdaQueryWrapper<Student> getCommonWrapper() {
        LambdaQueryWrapper<Student> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(Student::getGmtCreate);
        return queryWrapper;
    }
}
