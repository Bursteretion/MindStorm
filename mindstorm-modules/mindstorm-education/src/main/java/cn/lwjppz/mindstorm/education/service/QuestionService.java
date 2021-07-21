package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 题目表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
public interface QuestionService extends IService<Question> {

    /**
     * 通过题型Id获取该题型共有多少题
     *
     * @param questionTypeId 题型Id
     * @return 题目数量
     */
    Integer getCountByQuestionTypeId(String questionTypeId);

}
