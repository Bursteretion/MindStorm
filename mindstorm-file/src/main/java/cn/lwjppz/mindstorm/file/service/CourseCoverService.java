package cn.lwjppz.mindstorm.file.service;

import cn.lwjppz.mindstorm.file.model.entity.CourseCover;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-16
 */
public interface CourseCoverService extends IService<CourseCover> {

    /**
     * 上传课程封面图
     *
     * @param image 图片
     * @return 图片信息
     */
    CourseCover uploadCourseCover(MultipartFile image);

}
