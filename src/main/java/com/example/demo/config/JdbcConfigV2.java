package com.example.demo.config;

import com.example.demo.member.repository.MemberJdbcRepository;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import com.example.demo.member.service.MemberJdbcService;
import com.example.demo.member.service.MemberJdbcServiceV2;
import com.example.demo.write.repository.WriteJdbcRepositoryV2;
import com.example.demo.write.repository.WriteRepository;
import com.example.demo.write.service.WriteJdbcServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcConfigV2 {

    private final DataSource dataSource;

    @Bean
    public MemberJdbcServiceV2 memberJdbcServiceV2(){
        return new MemberJdbcServiceV2(memberJdbcRepositoryv2());
    }

    @Bean
    public MemberJdbcRepositoryV2 memberJdbcRepositoryv2(){
        return new MemberJdbcRepositoryV2(dataSource);
    }

    @Bean
    public WriteJdbcRepositoryV2 writeJdbcRepositoryV2(){
        return new WriteJdbcRepositoryV2(dataSource);
    }

    @Bean
    public WriteJdbcServiceV2 writeJdbcServiceV2(){
        return new WriteJdbcServiceV2(writeJdbcRepositoryV2());
    }

}
