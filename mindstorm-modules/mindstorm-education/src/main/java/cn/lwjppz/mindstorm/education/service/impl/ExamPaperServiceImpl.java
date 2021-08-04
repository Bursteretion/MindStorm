package cn.lwjppz.mindstorm.education.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.lwjppz.mindstorm.api.permission.feign.RemotePermissionFeignService;
import cn.lwjppz.mindstorm.api.permission.model.UserTo;
import cn.lwjppz.mindstorm.common.core.exception.EntityNotFoundException;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.mapper.ExamPaperMapper;
import cn.lwjppz.mindstorm.education.model.dto.exampaper.ExamPaperDTO;
import cn.lwjppz.mindstorm.education.model.dto.exampaper.ExamPaperDetailDTO;
import cn.lwjppz.mindstorm.education.model.entity.ExamPaper;
import cn.lwjppz.mindstorm.education.model.vo.exampaper.ExamPaperQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.exampaper.ExamPaperVO;
import cn.lwjppz.mindstorm.education.model.vo.exampaperquestion.ExamPaperQuestionVO;
import cn.lwjppz.mindstorm.education.service.ExamPaperQuestionService;
import cn.lwjppz.mindstorm.education.service.ExamPaperService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaper> implements ExamPaperService {

    private final RemotePermissionFeignService remotePermissionFeignService;
    private final ExamPaperQuestionService examPaperQuestionService;

    public ExamPaperServiceImpl(@Lazy RemotePermissionFeignService remotePermissionFeignService,
                                @Lazy ExamPaperQuestionService examPaperQuestionService) {
        this.remotePermissionFeignService = remotePermissionFeignService;
        this.examPaperQuestionService = examPaperQuestionService;
    }

    @Override
    public IPage<ExamPaperDTO> listExamPaper(ExamPaperQueryVO examPaperQueryVO) {
        LambdaQueryWrapper<ExamPaper> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(examPaperQueryVO.getCourseId())) {
            wrapper.eq(ExamPaper::getCourseId, examPaperQueryVO.getCourseId());
        }
        if (StringUtils.isNotEmpty(examPaperQueryVO.getTitle())) {
            wrapper.like(ExamPaper::getTitle, examPaperQueryVO.getTitle());
        }

        IPage<ExamPaper> page = new Page<>(examPaperQueryVO.getPageIndex(), examPaperQueryVO.getPageSize());
        page = baseMapper.selectPage(page, wrapper);

        IPage<ExamPaperDTO> resPage = new Page<>();
        BeanUtils.copyProperties(page, resPage);
        resPage.setRecords(convertToExamPaperDTO(page.getRecords()));

        return resPage;
    }

    @Override
    public ExamPaper createExamPaper(ExamPaperVO examPaperVO) {
        var examPaper = new ExamPaper();
        BeanUtil.copyProperties(examPaperVO, examPaper);

        baseMapper.insert(examPaper);

        return examPaper;
    }

    @Override
    public ExamPaper infoExamPaper(String examPaperId) {
        if (StringUtils.isNotEmpty(examPaperId)) {
            var examPaper = baseMapper.selectById(examPaperId);
            if (null == examPaper) {
                throw new EntityNotFoundException("该试卷没有找到！");
            }
            return examPaper;
        }
        return null;
    }

    @Override
    public boolean renameExamPaper(String examPaperId, String newName) {
        if (StringUtils.isNotEmpty(examPaperId) && StringUtils.isNotEmpty(newName)) {
            var examPaper = baseMapper.selectById(examPaperId);
            examPaper.setTitle(newName.strip());
            baseMapper.updateById(examPaper);
        }
        return true;
    }

    @Override
    public boolean updateExamPaper(ExamPaperVO examPaperVO) {
        var examPaper = new ExamPaper();
        BeanUtil.copyProperties(examPaperVO, examPaper);

        baseMapper.updateById(examPaper);

        // 新增试卷题目
        var examPaperQuestions = examPaperVO.getExamPaperQuestions();
        if (!CollectionUtils.isEmpty(examPaperQuestions)) {
            var examPaperQuestionsVOS = examPaperQuestions.stream().map(examPaperQuestionVO -> {
                var examPaperQuestion = new ExamPaperQuestionVO();
                examPaperQuestion.setExamPaperId(examPaper.getId());
                examPaperQuestion.setQuestionId(examPaperQuestionVO.getQuestionId());
                examPaperQuestion.setScore(examPaperQuestionVO.getScore());
                return examPaperQuestion;
            }).collect(Collectors.toList());
            // 新增试卷题目之前先将原试卷题目删除
            examPaperQuestionService.deleteExamPaperQuestions(examPaper.getId());
            // 新增
            examPaperQuestionService.insertExamPaperQuestions(examPaperQuestionsVOS);
        }
        return true;
    }

    @Override
    public boolean deleteExamPaper(String examPaperId) {
        if (StringUtils.isNotEmpty(examPaperId)) {
            // 删除试卷题目
            examPaperQuestionService.deleteExamPaperQuestions(examPaperId);
            // 删除试卷
            baseMapper.deleteById(examPaperId);
        }
        return true;
    }

    @Override
    public ExamPaperDTO convertToExamPaperDTO(ExamPaper examPaper) {
        var examPagerDTO = new ExamPaperDTO();
        BeanUtil.copyProperties(examPaper, examPagerDTO);

        var res = remotePermissionFeignService.remoteUserInfoById(examPaper.getUserId());
        var userTo = ServiceUtils.feignValueConvert(res.getData().get("userTo"), UserTo.class);
        examPagerDTO.setUserRealName(userTo.getRealName());

        return examPagerDTO;
    }

    @Override
    public List<ExamPaperDTO> convertToExamPaperDTO(List<ExamPaper> examPapers) {
        return examPapers.stream()
                .map(this::convertToExamPaperDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExamPaperDetailDTO convertToExamPaperDetailDTO(ExamPaper examPaper) {
        var examPaperDetailDTO = new ExamPaperDetailDTO();
        BeanUtils.copyProperties(examPaper, examPaperDetailDTO);

        var questions = examPaperQuestionService.listExamPaperQuestions(examPaper.getId());
        examPaperDetailDTO.setQuestions(questions);
        return examPaperDetailDTO;
    }


}
