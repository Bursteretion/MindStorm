package cn.lwjppz.mindstorm.common.swagger.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Swagger配置信息
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-25
 */
@Component
@Getter
@Setter
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    private String title;

    private String name;

    private String email;

    private String url;

    private String version;

    private String license;

    private String licenseUrl;

    private String termsOfServiceUrl;

    private String description;

}
