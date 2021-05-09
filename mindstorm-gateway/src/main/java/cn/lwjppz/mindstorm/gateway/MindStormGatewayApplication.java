package cn.lwjppz.mindstorm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : lwj
 * @since : 2021-05-08
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MindStormGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormGatewayApplication.class, args);
    }

}
