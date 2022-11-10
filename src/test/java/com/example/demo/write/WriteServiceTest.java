package com.example.demo.write;

import com.example.demo.SpringConfig;
import com.example.demo.member.Member;
import com.example.demo.member.MemberService;
import com.example.demo.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WriteServiceTest {

    MemberService memberService;
    WriteService writeService;

    @BeforeEach
    public void beforeEach(){
        SpringConfig springConfig = new SpringConfig();
        memberService = springConfig.memberService();
        writeService = springConfig.writeService();
    }

    @Test
    void createWrite(){
        Long memberId = 1L;
        Member member = new Member(memberId, "멤버1");
        memberService.join(member);

        Write write = writeService.createWrite(memberId,"제목","내용");

    }


}