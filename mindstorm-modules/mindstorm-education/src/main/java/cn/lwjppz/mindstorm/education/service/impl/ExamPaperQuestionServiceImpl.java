package cn.lwjppz.mindstorm.education.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.exampaperquestion.ExamPaperQuestionDTO;
import cn.lwjppz.mindstorm.education.model.entity.ExamPaperQuestion;
import cn.lwjppz.mindstorm.education.mapper.ExamPaperQuestionMapper;
import cn.lwjppz.mindstorm.education.model.vo.exampaperquestion.ExamPaperQuestionVO;
import cn.lwjppz.mindstorm.education.service.ExamPaperQuestionService;
import cn.lwjppz.mindstorm.education.service.QuestionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷题目表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
@Service
public class ExamPaperQuestionServiceImpl extends ServiceImpl<ExamPaperQuestionMapper, ExamPaperQuestion> implements ExamPaperQuestionService {

    private final QuestionService questionService;

    public ExamPaperQuestionServiceImpl(@Lazy QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public List<ExamPaperQuestionDTO> listExamPaperQuestions(String examPaperId) {
        LambdaQueryWrapper<ExamPaperQuestion> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ExamPaperQuestion::getExamPaperId, examPaperId);
        var examPaperQuestions = baseMapper.selectList(wrapper);
        return examPaperQuestions.stream()
                .map(examPaperQuestion -> {
                    var examPaperQuestionDTO = new ExamPaperQuestionDTO();
                    examPaperQuestionDTO.setQuestionId(examPaperQuestion.getQuestionId());
                    var question = questionService.infoQuestion(examPaperQuestion.getQuestionId());
                    examPaperQuestionDTO.setQuestionContent(question.getOriginalContent());
                    examPaperQuestionDTO.setScore(examPaperQuestion.getScore());
                    return examPaperQuestionDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public boolean insertExamPaperQuestions(List<ExamPaperQuestionVO> examPaperQuestions) {
        if (!CollectionUtils.isEmpty(examPaperQuestions)) {
            examPaperQuestions.forEach(examPaperQuestionVO -> {
                var examPaperQuestion = new ExamPaperQuestion();
                BeanUtil.copyProperties(examPaperQuestionVO, examPaperQuestion);
                baseMapper.insert(examPaperQuestion);
            });
        }
        return true;
    }

    @Override
    public boolean deleteExamPaperQuestions(String examPaperId) {
        if (StringUtils.isNotEmpty(examPaperId)) {
            LambdaQueryWrapper<ExamPaperQuestion> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ExamPaperQuestion::getExamPaperId, examPaperId);
            baseMapper.delete(wrapper);
        }
        return true;
    }
}
