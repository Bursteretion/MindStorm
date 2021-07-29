package cn.lwjppz.mindstorm.education.listener;

import cn.lwjppz.mindstorm.education.model.vo.question.QuestionImportVO;
import cn.lwjppz.mindstorm.education.service.QuestionService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-29
 */
public class QuestionImportListener extends AnalysisEventListener<QuestionImportVO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionImportListener.class);

    /**
     * 每隔5条存储数据库
     */
    private static final int BATCH_COUNT = 5;
    private final List<QuestionImportVO> questionImports = new ArrayList<>();
    private final QuestionService questionService;

    public QuestionImportListener(@Lazy QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void invoke(QuestionImportVO questionImportVO, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(questionImportVO));
        questionImports.add(questionImportVO);
        if (questionImports.size() >= BATCH_COUNT) {
            processImport();
            questionImports.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        questionService.importQuestion(questionImports);
        LOGGER.info("所有题目导入完成！");
    }

    private void processImport() {
        LOGGER.info("{}条数据，开始导入题目！", questionImports.size());
        questionService.importQuestion(questionImports);
        LOGGER.info("导入题目成功！");
    }
}
