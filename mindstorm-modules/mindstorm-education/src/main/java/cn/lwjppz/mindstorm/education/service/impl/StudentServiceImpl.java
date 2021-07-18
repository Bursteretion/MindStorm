package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.mapper.StudentMapper;
import cn.lwjppz.mindstorm.education.model.dto.student.StudentDTO;
import cn.lwjppz.mindstorm.education.model.dto.student.StudentDetailDTO;
import cn.lwjppz.mindstorm.education.model.entity.Student;
import cn.lwjppz.mindstorm.education.model.vo.student.StudentQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.student.StudentVO;
import cn.lwjppz.mindstorm.education.service.AcademyService;
import cn.lwjppz.mindstorm.education.service.ProfessionService;
import cn.lwjppz.mindstorm.education.service.StudentService;
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

    private final AcademyService academyService;
    private final ProfessionService professionService;

    public StudentServiceImpl(@Lazy AcademyService academyService,
                              @Lazy ProfessionService professionService) {
        this.academyService = academyService;
        this.professionService = professionService;
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

        IPage<Student> page = baseMapper.selectPage(new Page<>(studentQueryVO.getPageIndex(),
                studentQueryVO.getPageSize()), wrapper);

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
            var academy = academyService.infoAcademy(student.getAcademyId());
            if (null != academy) {
                studentDTO.setAcademyName(academy.getName());
            }
        }
        if (StringUtils.isNotEmpty(student.getProfessionId())) {
            var profession = professionService.infoProfession(student.getProfessionId());
            if (null != profession) {
                studentDTO.setProfessionName(profession.getName());
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
