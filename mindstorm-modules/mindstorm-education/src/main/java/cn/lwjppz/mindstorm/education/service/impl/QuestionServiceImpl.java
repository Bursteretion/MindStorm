package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.api.permission.feign.RemotePermissionFeignService;
import cn.lwjppz.mindstorm.api.permission.model.UserTo;
import cn.lwjppz.mindstorm.common.core.enums.type.QuestionDifficultyType;
import cn.lwjppz.mindstorm.common.core.support.ValueEnum;
import cn.lwjppz.mindstorm.common.core.utils.ServiceUtils;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.question.QuestionDTO;
import cn.lwjppz.mindstorm.education.model.entity.Question;
import cn.lwjppz.mindstorm.education.mapper.QuestionMapper;
import cn.lwjppz.mindstorm.education.model.entity.QuestionTopic;
import cn.lwjppz.mindstorm.education.model.vo.question.QuestionQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.question.QuestionVO;
import cn.lwjppz.mindstorm.education.model.vo.questionanswer.QuestionAnswerVO;
import cn.lwjppz.mindstorm.education.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    private final CourseService courseService;
    private final QuestionTypeService questionTypeService;
    private final QuestionTopicService questionTopicService;
    private final QuestionOptionService questionOptionService;
    private final QuestionAnswerService questionAnswerService;
    private final RemotePermissionFeignService remotePermissionFeignService;

    public QuestionServiceImpl(@Lazy CourseService courseService,
                               @Lazy QuestionTypeService questionTypeService,
                               @Lazy QuestionTopicService questionTopicService,
                               @Lazy QuestionOptionService questionOptionService,
                               @Lazy QuestionAnswerService questionAnswerService,
                               @Lazy RemotePermissionFeignService remotePermissionFeignService) {
        this.courseService = courseService;
        this.questionTypeService = questionTypeService;
        this.questionTopicService = questionTopicService;
        this.questionOptionService = questionOptionService;
        this.questionAnswerService = questionAnswerService;
        this.remotePermissionFeignService = remotePermissionFeignService;
    }

    @Override
    public Integer getCountByQuestionTypeId(String questionTypeId) {
        if (StringUtils.isNotEmpty(questionTypeId)) {
            LambdaQueryWrapper<Question> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Question::getQuestionTypeId, questionTypeId);
            return baseMapper.selectCount(wrapper);
        }
        return null;
    }

    @Override
    public IPage<QuestionDTO> listQuestions(QuestionQueryVO questionQueryVO) {
        LambdaQueryWrapper<Question> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(questionQueryVO.getPid())) {
            wrapper.eq(Question::getPid, questionQueryVO.getPid());
        }
        if (StringUtils.isNotEmpty(questionQueryVO.getCourseId())) {
            wrapper.eq(Question::getCourseId, questionQueryVO.getCourseId());
        }
        if (StringUtils.isNotEmpty(questionQueryVO.getQuestionTypeId())) {
            wrapper.eq(Question::getQuestionTypeId, questionQueryVO.getQuestionTypeId());
        }
        if (StringUtils.isNotEmpty(questionQueryVO.getTopicId())) {
            var questions = questionTopicService.listQuestionTopicByTopicId(questionQueryVO.getTopicId());
            var questionIds = ServiceUtils.fetchProperty(questions, QuestionTopic::getQuestionId);
            wrapper.in(Question::getId, questionIds);
        }
        if (StringUtils.isNotEmpty(questionQueryVO.getContent())) {
            wrapper.like(Question::getContent, questionQueryVO.getContent());
        }
        if (null != questionQueryVO.getDifficulty()) {
            wrapper.eq(Question::getDifficulty, questionQueryVO.getDifficulty());
        }
        wrapper.orderByDesc(Question::getSort).orderByDesc(Question::getGmtCreate);

        IPage<Question> page = new Page<>(questionQueryVO.getPageIndex(), questionQueryVO.getPageSize());
        page = baseMapper.selectPage(page, wrapper);

        IPage<QuestionDTO> resPage = new Page<>();
        BeanUtils.copyProperties(page, resPage);

        resPage.setRecords(convertToQuestionDTO(page.getRecords()));
        return resPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createQuestion(QuestionVO questionVO) {
        var question = new Question();
        var isFolder = questionVO.getIsFolder();
        BeanUtils.copyProperties(questionVO, question);
        if (isFolder) {
            question.setSort(1);
            baseMapper.insert(question);
        } else {
            // 新增题目
            question.setSort(0);
            baseMapper.insert(question);

            // 新增题目选项
            var optionIds = questionOptionService.createQuestionOptions(question.getId(), questionVO.getOptions());

            // 新增题目答案
            var answers = questionVO.getAnswers();
            if (CollectionUtils.isEmpty(answers)) {
                var questionAnswer = new QuestionAnswerVO();
                questionAnswer.setQuestionId(question.getId());
                questionAnswer.setAnalyze(questionVO.getAnswerAnalyze());
                questionAnswer.setValue(questionVO.getAnswerValue());
                questionAnswer.setQuestionId(optionIds.get(questionVO.getAnswerIndex()));
                questionAnswerService.createQuestionAnswer(questionAnswer);
            } else {
                for (int i = 0; i < answers.size(); i++) {
                    var questionAnswer = new QuestionAnswerVO();
                    questionAnswer.setQuestionId(question.getId());
                    if (i == 0) {
                        questionAnswer.setAnalyze(questionVO.getAnswerAnalyze());
                    }
                    questionAnswer.setValue(answers.get(i).getValue());
                    questionAnswerService.createQuestionAnswer(questionAnswer);
                }
            }
        }
        return true;
    }

    @Override
    public boolean updateQuestion(QuestionVO questionVO) {
        return false;
    }

    @Override
    public boolean deleteQuestion(String questionId) {
        return false;
    }

    @Override
    public QuestionDTO convertToQuestionDTO(Question question) {
        var questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        if (StringUtils.isNotEmpty(question.getQuestionTypeId())) {
            var questionType = questionTypeService.getById(question.getQuestionTypeId());
            questionDTO.setQuestionType(questionType.getName());
        }

        if (null != question.getDifficulty()) {
            var difficult = ValueEnum.valueToEnum(QuestionDifficultyType.class, question.getDifficulty());
            questionDTO.setDifficulty(difficult.getName());
        }

        var res = remotePermissionFeignService.remoteUserInfoById(question.getUserId());
        var userTo = ServiceUtils.feignValueConvert(res.getData().get("userTo"), UserTo.class);
        questionDTO.setUserRealName(userTo.getRealName());

        var course = courseService.infoCourse(question.getCourseId());
        questionDTO.setCourseName(course.getName());

        return questionDTO;
    }

    @Override
    public List<QuestionDTO> convertToQuestionDTO(List<Question> questions) {
        return questions.stream()
                .map(this::convertToQuestionDTO)
                .collect(Collectors.toList());
    }
}
