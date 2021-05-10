package cn.lwjppz.mindstorm.security.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * <p></p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@EnableKnife4j
@ConfigurationProperties(prefix = "swagger")
@Getter
@Setter
public class SwaggerConfig {

    private String title;
    private String name;
    private String email;
    private String url;
    private String version;
    private String license;
    private String licenseUrl;
    private String termsOfServiceUrl;
    private String description;

    /**
     * 创建获取api应用
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置swagger文档显示的相关内容标识(信息会显示到swagger页面)
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(title,
                description,
                version,
                termsOfServiceUrl,
                new Contact(name, email, url),
                license,
                licenseUrl,
                new ArrayList<>());
    }
}
