package cn.lwjppz.mindstorm.file.config;

import cn.lwjppz.mindstorm.common.core.constant.Constants;
import cn.lwjppz.mindstorm.file.support.FileProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC configuration
 *
 * @author : lwj
 * @since : 2021-07-15
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final FileProperties fileProperties;

    public WebMvcConfiguration(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String workDir = Constants.FILE_PROTOCOL + fileProperties.getWorkDir() + Constants.FILE_SEPARATOR;
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(workDir + "upload/");
    }
}
