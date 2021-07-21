package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.questiontype.QuestionTypeDTO;
import cn.lwjppz.mindstorm.education.model.dto.questiontype.QuestionTypeSelectDTO;
import cn.lwjppz.mindstorm.education.model.entity.QuestionType;
import cn.lwjppz.mindstorm.education.model.vo.questiontype.QuestionTypeSimpleVO;
import cn.lwjppz.mindstorm.education.model.vo.questiontype.QuestionTypeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题目类型（题型）表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
public interface QuestionTypeService extends IService<QuestionType> {

    /**
     * 获取所有题型
     *
     * @return 题型列表
     */
    List<QuestionType> listQuestionTypes();

    /**
     * 新增题型
     *
     * @param questionTypeVO 题型信息
     * @return 题型信息
     */
    QuestionType createQuestionType(QuestionTypeVO questionTypeVO);

    /**
     * 更改题型名称
     *
     * @param questionTypeSimpleVO 题型信息
     * @return 是否更改成功
     */
    boolean updateQuestionType(QuestionTypeSimpleVO questionTypeSimpleVO);

    /**
     * 根据题型Id删除该题型
     *
     * @param questionTypeId 题型Id
     * @return 是否删除成功
     */
    boolean deleteQuestionType(String questionTypeId);

    /**
     * 将 QuestionType 对象转为 QuestionTypeDTO 对象
     *
     * @param questionType QuestionType 对象
     * @return QuestionTypeDTO 对象
     */
    QuestionTypeDTO convertQuestionTypeDTO(QuestionType questionType);

    /**
     * 将 QuestionType 对象集合转为 QuestionTypeDTO 对象集合
     *
     * @param questionTypes QuestionType 对象集合
     * @return QuestionTypeDTO 对象集合
     */
    List<QuestionTypeDTO> convertQuestionTypeDTO(List<QuestionType> questionTypes);

    /**
     * 将 QuestionType 对象转为 QuestionTypeSelectDTO 对象
     *
     * @param questionType QuestionType 对象
     * @return QuestionTypeSelectDTO 对象
     */
    QuestionTypeSelectDTO convertToQuestionTypeSelectDTO(QuestionType questionType);

    /**
     * 将 QuestionType 对象集合转为 QuestionTypeSelectDTO 对象集合
     *
     * @param questionTypes QuestionType 对象集合
     * @return QuestionTypeSelectDTO 对象集合
     */
    List<QuestionTypeSelectDTO> convertToQuestionTypeSelectDTO(List<QuestionType> questionTypes);

}
