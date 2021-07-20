package cn.lwjppz.mindstorm.education.service.impl;

import cn.lwjppz.mindstorm.common.core.support.ValueEnum;
import cn.lwjppz.mindstorm.common.core.utils.StringUtils;
import cn.lwjppz.mindstorm.education.model.dto.questiontype.QuestionTypeDTO;
import cn.lwjppz.mindstorm.education.model.entity.QuestionType;
import cn.lwjppz.mindstorm.education.mapper.QuestionTypeMapper;
import cn.lwjppz.mindstorm.education.model.vo.questiontype.QuestionTypeSimpleVO;
import cn.lwjppz.mindstorm.education.model.vo.questiontype.QuestionTypeVO;
import cn.lwjppz.mindstorm.education.service.QuestionTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 题目类型（题型）表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@Service
public class QuestionTypeServiceImpl extends ServiceImpl<QuestionTypeMapper, QuestionType> implements QuestionTypeService {

    @Override
    public List<QuestionType> listQuestionTypes() {
        return baseMapper.selectList(null);
    }

    @Override
    public QuestionType createQuestionType(QuestionTypeVO questionTypeVO) {
        var questionType = new QuestionType();
        BeanUtils.copyProperties(questionTypeVO, questionType);

        baseMapper.insert(questionType);
        return questionType;
    }

    @Override
    public boolean updateQuestionType(QuestionTypeSimpleVO questionTypeSimpleVO) {
        if (StringUtils.isNotEmpty(questionTypeSimpleVO.getId())) {
            var questionType = baseMapper.selectById(questionTypeSimpleVO.getId());
            questionType.setName(questionTypeSimpleVO.getName());
            baseMapper.updateById(questionType);
        }
        return true;
    }

    @Override
    public boolean deleteQuestionType(String questionTypeId) {
        if (StringUtils.isNotEmpty(questionTypeId)) {
            baseMapper.deleteById(questionTypeId);
        }
        return true;
    }

    @Override
    public QuestionTypeDTO convertQuestionTypeDTO(QuestionType questionType) {
        var questionTypeDTO = new QuestionTypeDTO();
        BeanUtils.copyProperties(questionType, questionTypeDTO);
        var questionTypeEnum = ValueEnum.valueToEnum(cn.lwjppz.mindstorm.common.core.enums.type.QuestionType.class,
                questionType.getType());
        questionTypeDTO.setTypeName(questionTypeEnum.getName());
        return questionTypeDTO;
    }

    @Override
    public List<QuestionTypeDTO> convertQuestionTypeDTO(List<QuestionType> questionTypes) {
        return questionTypes.stream()
                .map(this::convertQuestionTypeDTO)
                .collect(Collectors.toList());
    }
}
