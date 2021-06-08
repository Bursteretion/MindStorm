package cn.lwjppz.mindstorm.knowledge.service.impl;

import cn.lwjppz.mindstorm.common.core.enums.status.ResultStatus;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.knowledge.model.dto.subject.SubjectDTO;
import cn.lwjppz.mindstorm.knowledge.model.dto.subject.SubjectDetailDTO;
import cn.lwjppz.mindstorm.knowledge.model.entity.Subject;
import cn.lwjppz.mindstorm.knowledge.mapper.SubjectMapper;
import cn.lwjppz.mindstorm.knowledge.model.vo.subject.SubjectVO;
import cn.lwjppz.mindstorm.knowledge.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 学科服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-05-27
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public List<Subject> getSubjects() {
        LambdaQueryWrapper<Subject> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Subject::getGmtCreate);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Subject> getSubjects(Integer level) {
        LambdaQueryWrapper<Subject> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Subject::getLevel, level);
        queryWrapper.orderByAsc(Subject::getGmtCreate);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<SubjectDTO> pageBy(int pageIndex, int pageSize) {
        IPage<Subject> iPage = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<Subject> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Subject::getGmtCreate);
        iPage = baseMapper.selectPage(iPage, queryWrapper);

        IPage<SubjectDTO> resPage = new Page<>();
        List<Subject> records = iPage.getRecords();
        resPage.setRecords(convertToSubjectDTO(records));

        BeanUtils.copyProperties(iPage, resPage);

        return resPage;
    }

    @Override
    public Subject insertSubject(SubjectVO subjectVO) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectVO, subject);

        baseMapper.insert(subject);

        return getSubject(subject.getId());
    }

    @Override
    public Subject getSubject(String subjectId) {
        if (StringUtils.isNotEmpty(subjectId)) {
            return baseMapper.selectById(subjectId);
        }
        return null;
    }

    @Override
    public Subject updateSubject(SubjectVO subjectVO) {
        if (StringUtils.isNotEmpty(subjectVO.getId()) && null == getSubject(subjectVO.getId())) {
            throw new EntityNotFoundException(ResultStatus.ENTITY_NOT_FOUND);
        }

        var subject = new Subject();
        BeanUtils.copyProperties(subjectVO, subject);

        baseMapper.updateById(subject);

        return getSubject(subject.getId());
    }

    @Override
    public boolean deleteSubject(String subjectId) {
        if (StringUtils.isNotEmpty(subjectId)) {
            baseMapper.deleteById(subjectId);
        }
        return true;
    }

    @Override
    public boolean deleteBatchSubjects(List<String> subjectIds) {
        return subjectIds.stream()
                .filter(StringUtils::isNotEmpty)
                .allMatch(this::deleteSubject);
    }

    @Override
    public SubjectDTO convertToSubjectDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        BeanUtils.copyProperties(subject, subjectDTO);

        return subjectDTO;
    }

    @Override
    public List<SubjectDTO> convertToSubjectDTO(List<Subject> subjects) {
        return subjects.stream()
                .map(this::convertToSubjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDetailDTO convertToSubjectDetailDTO(Subject subject) {
        SubjectDetailDTO subjectDetailDTO = new SubjectDetailDTO();
        BeanUtils.copyProperties(subject, subjectDetailDTO);

        return subjectDetailDTO;
    }

    @Override
    public List<SubjectDetailDTO> convertToSubjectDetailDTO(List<Subject> subjects) {
        return subjects.stream()
                .map(this::convertToSubjectDetailDTO)
                .collect(Collectors.toList());
    }
}
