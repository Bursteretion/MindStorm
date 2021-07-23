package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.questionoption.QuestionOptionDTO;
import cn.lwjppz.mindstorm.education.model.entity.QuestionOption;
import cn.lwjppz.mindstorm.education.model.vo.questionoption.QuestionOptionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题目选项表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
public interface QuestionOptionService extends IService<QuestionOption> {

    /**
     * 根据题目Id获取题目选项列表
     *
     * @param questionId 题目Id
     * @return 题目选项列表
     */
    List<QuestionOptionDTO> getQuestionOptions(String questionId);

    /**
     * 新增题目选项
     *
     * @param questionId      题目Id
     * @param questionOptions 题目选项列表
     * @return 选项Id集合
     */
    List<String> createQuestionOptions(String questionId, List<QuestionOptionVO> questionOptions);

}
