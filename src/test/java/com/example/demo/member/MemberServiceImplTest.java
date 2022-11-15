package com.example.demo.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    void join(){
        //given & when
        Member expected = memberService.join(new Member(0L,"태용","라이언"));

        //then
        Member actual = memberService.findOneMember(expected.getId()).get();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void duplicateNicknameCheck(){
        //given
        Member member1 = new Member(0L,"태용","라이언");
        Member member2 = new Member(1L,"원진","라이언");

        //when
        memberService.join(member1);

        //then
        IllegalStateException exception =
                assertThrows(IllegalStateException.class, ()->memberService.join(member2));
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 닉네임입니다");

    }

    @Test
    void findAllMember(){
        //given & when
        memberService.join(new Member(0L,"태용","라이언"));
        memberService.join(new Member(1L,"원진","프로도"));

        List<Member> result = memberService.findAllMember();

        //then
        assertThat(result.size()).isEqualTo(2);
        
    }

    @Test
    void findOneMember(){
        //given & when
        Member expected = memberService.join(new Member(0L, "태용", "라이언"));

        Member actual = memberService.findOneMember(expected.getId()).get();

        //then
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void deleteMember(){
        //given & when
        Member expected = memberService.join(new Member(0L, "태용", "라이언"));

        memberService.deleteMember(expected.getId());

        //then
        assertThat(memberService.findOneMember(expected.getId())).isEmpty();
    }



}