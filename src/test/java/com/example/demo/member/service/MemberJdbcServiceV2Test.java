package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberJdbcServiceV2Test {

    @Autowired
    MemberJdbcRepositoryV2 memberJdbcRepositoryV2;

    @Autowired
    MemberJdbcServiceV2 memberJdbcServiceV2;

    @BeforeEach
    void clear(){
        memberJdbcRepositoryV2.clear( );
    }

    @Test
    void join() {
        //given & when
        Member expected = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));

        //then
        Optional<Member> actual = memberJdbcServiceV2.findOneMember(expected.getId());

        assertThat(actual.get().getId()).isEqualTo(expected.getId());

    }

    @Test
    void duplicateNicknameCheck(){
        //given
        Member member1 = new Member(null, "태용", "라이언");
        Member member2 = new Member(null, "원진", "라이언");
        //when
        memberJdbcServiceV2.join(member1);
        try{
            memberJdbcServiceV2.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 닉네임입니다");
        }

    }

    @Test
    void findAllMember() {
        //given
        Member expected1 = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));
        Member expected2 = memberJdbcServiceV2.join(new Member(null, "원진", "프로도"));

        //when
        List<Member> actual = memberJdbcServiceV2.findAllMember();

        //then
        assertThat(actual.size()).isEqualTo(2);

        assertThat(expected1.getId()).isEqualTo(actual.get(0).getId());
        assertThat(expected2.getId()).isEqualTo(actual.get(1).getId());

        assertThat(expected1.getName()).isEqualTo(actual.get(0).getName());
        assertThat(expected2.getName()).isEqualTo(actual.get(1).getName());

        assertThat(expected1.getNickname()).isEqualTo(actual.get(0).getNickname());
        assertThat(expected2.getNickname()).isEqualTo(actual.get(1).getNickname());
    }

    @Test
    void findOneMember() {
        //given
        Member expected = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));

        //when
        Optional<Member> actual = memberJdbcServiceV2.findOneMember(expected.getId());

        //then
        assertThat(actual.get().getId()).isEqualTo(expected.getId());
    }

    @Test
    void deleteMember() {
        //given
        Member expected = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));

        //when
        memberJdbcServiceV2.deleteMember(expected.getId());

        //then
        assertThrows(NoSuchElementException.class,()->memberJdbcServiceV2.findOneMember(expected.getId()));
    }

}