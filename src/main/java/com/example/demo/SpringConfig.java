package com.example.demo;

import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberService;
import com.example.demo.member.MemberServiceImpl;
import com.example.demo.member.MemoryMemberRepository;
import com.example.demo.write.MemoryWriteRepository;
import com.example.demo.write.WriteRepository;
import com.example.demo.write.WriteService;
import com.example.demo.write.WriteServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
