package com.example.demo.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoryMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save(){
        //given
        Member member = new Member(0L,"bty","ryan");
        //when
        memberRepository.save(member);

        //then
        Long result = memberRepository.findById(member.getId()).getId();

        Assertions.assertThat(result).isEqualTo(member.getId());

    }

    @Test
    void findById(){
        //given
        Member member = new Member(0L,"태용","라이언");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(member.getId());

        //then
        assertThat(findMember).isEqualTo(member);
    }

}