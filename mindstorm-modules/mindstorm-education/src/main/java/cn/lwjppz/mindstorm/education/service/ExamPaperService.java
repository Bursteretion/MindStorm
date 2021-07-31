package cn.lwjppz.mindstorm.education.service;

import cn.lwjppz.mindstorm.education.model.dto.exampaper.ExamPaperDTO;
import cn.lwjppz.mindstorm.education.model.entity.ExamPaper;
import cn.lwjppz.mindstorm.education.model.vo.exampaper.ExamPaperQueryVO;
import cn.lwjppz.mindstorm.education.model.vo.exampaper.ExamPaperVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author lwj
 * @since 2021-07-31
 */
public interface ExamPaperService extends IService<ExamPaper> {

    /**
     * 获取该课程所有试卷
     *
     * @param examPaperQueryVO 查询条件
     * @return 试卷列表信息
     */
    IPage<ExamPaperDTO> listExamPaper(ExamPaperQueryVO examPaperQueryVO);

    /**
     * 新建试卷
     *
     * @param examPaperVO 试卷信息
     * @return 试卷信息
     */
    ExamPaper createExamPaper(ExamPaperVO examPaperVO);

    /**
     * 更改试卷名
     *
     * @param examPaperId 试卷Id
     * @param newName     新试卷名
     * @return 是否更改成功
     */
    boolean renameExamPaper(String examPaperId, String newName);

    /**
     * 更新试卷信息
     *
     * @param examPaperVO 试卷信息
     * @return 是否更新成功
     */
    boolean updateExamPaper(ExamPaperVO examPaperVO);

    /**
     * 将 ExamPaper 对象转为 ExamPaperDTO 对象
     *
     * @param examPaper ExamPaper 对象
     * @return ExamPaperDTO 对象
     */
    ExamPaperDTO convertToExamPaperDTO(ExamPaper examPaper);

    /**
     * 将 ExamPaper 对象集合转为 ExamPaperDTO 对象集合
     *
     * @param examPapers ExamPaper 对象集合
     * @return ExamPaperDTO 对象集合
     */
    List<ExamPaperDTO> convertToExamPaperDTO(List<ExamPaper> examPapers);

}
