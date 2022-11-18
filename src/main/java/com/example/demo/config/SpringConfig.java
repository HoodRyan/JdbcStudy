package com.example.demo.config;

import com.example.demo.member.repository.MemberJdbcRepository;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberJdbcService;
import com.example.demo.member.service.MemberService;
import com.example.demo.member.service.MemberServiceImpl;
import com.example.demo.member.repository.MemoryMemberRepository;
import com.example.demo.write.MemoryWriteRepository;
import com.example.demo.write.WriteRepository;
import com.example.demo.write.WriteService;
import com.example.demo.write.WriteServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public WriteService writeService(){
        return new WriteServiceImpl(writeRepository());
    }

    @Bean
    public WriteRepository writeRepository(){
        return new MemoryWriteRepository();
    }
}
