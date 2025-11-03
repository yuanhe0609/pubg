package com.dataAnalysis.pubgSeasonService;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.dataAnalysis.pubgSeasonService.mapper")
public class PubgSeasonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubgSeasonServiceApplication.class, args);
    }

}
