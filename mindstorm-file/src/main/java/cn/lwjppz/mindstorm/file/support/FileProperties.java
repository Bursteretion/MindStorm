package cn.lwjppz.mindstorm.file.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static cn.lwjppz.mindstorm.common.core.constant.Constants.*;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-07-15
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "mindstorm")
public class FileProperties {

    /**
     * word dir
     */
    private String workDir = USER_HOME + FILE_SEPARATOR + ".mindstorm" + FILE_SEPARATOR;

    /**
     * Eternal data backup dir
     */
    private String backupDir = workDir + "backup" + FILE_SEPARATOR;

    /**
     * Eternal data export dir
     */
    private String dataExportDir = workDir + "data-export" + FILE_SEPARATOR;

    /**
     * Eternal upload dir
     */
    private String uploadDir = workDir + "upload" + FILE_SEPARATOR;

    /**
     * upload dir prefix
     */
    private String uploadUrlPrefix = "upload";

    /**
     * Download Timeout.
     */
    private Duration downloadTimeout = Duration.ofSeconds(30);

}
