package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.api.permission.feign.RemotePermissionFeignService;
import cn.lwjppz.mindstorm.api.permission.model.UserTo;
import cn.lwjppz.mindstorm.common.core.enums.type.QuestionDifficultyType;
import cn.lwjppz.mindstorm.common.core.enums.type.QuestionType;
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

import java.util.ArrayList;
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

            int questionType = questionVO.getQuestionType();
            List<String> optionIds = new ArrayList<>();
            if (questionVO.getOptions().size() != 0) {
                // 新增题目选项
                optionIds = questionOptionService.createQuestionOptions(question.getId(), questionVO.getOptions());
            }
            if (questionType == QuestionType.SINGLE_CHOICE.getValue()) {
                // 新增题目答案
                var questionAnswer = new QuestionAnswerVO();
                questionAnswer.setQuestionId(question.getId());
                questionAnswer.setQuestionId(optionIds.get(questionVO.getAnswerIndex().get(0)));
                questionAnswerService.createQuestionAnswer(questionAnswer);
            } else if (questionType == QuestionType.MULTIPLE_CHOICE.getValue()) {
                var answerIndex = questionVO.getAnswerIndex();
                List<String> finalOptionIds = optionIds;
                answerIndex.forEach(index -> {
                    var questionAnswer = new QuestionAnswerVO();
                    questionAnswer.setQuestionId(question.getId());
                    questionAnswer.setQuestionId(finalOptionIds.get(index));
                    questionAnswerService.createQuestionAnswer(questionAnswer);
                });
            } else if (questionType == QuestionType.FILL_BLANK.getValue()) {
                var answers = questionVO.getAnswers();
                answers.forEach(questionAnswerVO -> {
                    questionAnswerVO.setQuestionId(question.getId());
                    questionAnswerService.createQuestionAnswer(questionAnswerVO);
                });
            } else {
                var questionAnswer = new QuestionAnswerVO();
                questionAnswer.setQuestionId(question.getId());
                questionAnswer.setValue(questionVO.getAnswerValue());
                questionAnswerService.createQuestionAnswer(questionAnswer);
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
