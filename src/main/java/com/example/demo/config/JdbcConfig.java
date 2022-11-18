package com.example.demo.config;

import com.example.demo.member.repository.MemberJdbcRepository;
import com.example.demo.member.service.MemberJdbcService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    private final DataSource dataSource;

    public JdbcConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberJdbcService memberJdbcService(){
        return new MemberJdbcService(memberJdbcRepository());
    }

    @Bean
    public MemberJdbcRepository memberJdbcRepository(){
        return new MemberJdbcRepository(dataSource);
    }




}
