package com.example.demo.member;

import com.example.demo.write.Write;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoryMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save(){
        //given
        Member member = new Member(0L,"bty","ryan");
        //when
        Member save = memberRepository.save(member);

        //then
        Assertions.assertThat(save.getId()).isEqualTo(1L);

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