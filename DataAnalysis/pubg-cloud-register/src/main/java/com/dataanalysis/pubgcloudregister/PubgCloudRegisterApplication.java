package com.dataanalysis.pubgcloudregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PubgCloudRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubgCloudRegisterApplication.class, args);
    }

}
