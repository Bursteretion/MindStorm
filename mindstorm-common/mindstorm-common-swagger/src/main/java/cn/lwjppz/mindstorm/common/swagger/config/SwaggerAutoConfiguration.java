package cn.lwjppz.mindstorm.common.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * <p>
 * Swagger自动配置类
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-25
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@EnableAutoConfiguration
@Import(BeanValidatorPluginsConfiguration.class)
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
public class SwaggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    /**
     * 创建获取api应用
     */
    @Bean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置swagger文档显示的相关内容标识(信息会显示到swagger页面)
     */
    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfo(swaggerProperties.getTitle(),
                swaggerProperties.getDescription(),
                swaggerProperties.getVersion(),
                swaggerProperties.getTermsOfServiceUrl(),
                new Contact(swaggerProperties.getName(), swaggerProperties.getEmail(), swaggerProperties.getUrl()),
                swaggerProperties.getLicense(),
                swaggerProperties.getLicenseUrl(),
                new ArrayList<>());
    }
}
