package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemoryMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void clear(){
        memberRepository.clear();
    }

    @Test
    void save(){
        //given & when
        Member expected = memberRepository.save(new Member(null, "bty", "ryan"));
        //then
        Member actual = memberRepository.findById(expected.getId()).get();
        Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());

    }

    @Test
    void findById(){
        //given
        Member expected = memberRepository.save(new Member(null, "태용", "라이언"));
        //when
        Member actual = memberRepository.findById(expected.getId()).get();
        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void findByNickname(){
        //given
        Member expected = memberRepository.save(new Member(null, "태용", "라이언"));
        //when
        Member actual = memberRepository.findByNickname(expected.getNickname()).get();
        //then
        assertThat(actual.getNickname()).isEqualTo(expected.getNickname());

    }

    @Test
    void findAll(){
        //given
        memberRepository.save(new Member(null, "태용", "라이언"));
        memberRepository.save(new Member(null, "원진", "프로도"));

        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void delete(){
        //given
        Member expected1 = memberRepository.save(new Member(null, "태용", "라이언"));

        //when
        memberRepository.delete(expected1.getId());

        //then
        assertThat(memberRepository.findById(expected1.getId())).isEmpty();



    }

}