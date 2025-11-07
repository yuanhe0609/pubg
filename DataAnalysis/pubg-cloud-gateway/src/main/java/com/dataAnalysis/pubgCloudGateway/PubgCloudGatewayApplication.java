package com.dataAnalysis.pubgCloudGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.dataAnalysis"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PubgCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubgCloudGatewayApplication.class, args);
    }

}
