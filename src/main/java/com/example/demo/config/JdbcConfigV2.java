package com.example.demo.config;

import com.example.demo.member.repository.MemberJdbcRepository;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import com.example.demo.member.service.MemberJdbcService;
import com.example.demo.member.service.MemberJdbcServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfigV2 {

    private final DataSource dataSource;

    public JdbcConfigV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberJdbcServiceV2 memberJdbcServiceV2(){
        return new MemberJdbcServiceV2(memberJdbcRepositoryv2());
    }

    @Bean
    public MemberJdbcRepositoryV2 memberJdbcRepositoryv2(){
        return new MemberJdbcRepositoryV2(dataSource);
    }




}
