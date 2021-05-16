package cn.lwjppz.mindstorm.permission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lwj
 * @since 2021-05-10
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MindStormPermissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindStormPermissionApplication.class, args);
    }

}
