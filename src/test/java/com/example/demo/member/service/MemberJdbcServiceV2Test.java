package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.demo.common.MemberFixtures.멤버_회원가입1;
import static com.example.demo.common.MemberFixtures.멤버_회원가입2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberJdbcServiceV2Test {

    @Autowired
    MemberJdbcRepositoryV2 memberJdbcRepositoryV2;

    @Autowired
    MemberJdbcServiceV2 memberJdbcServiceV2;

    @BeforeEach
    void clear(){
        memberJdbcRepositoryV2.clear();
    }


    @Test
    @DisplayName("회원 가입")
    void join() {
        //given & when
        Member expected = memberJdbcServiceV2.join(멤버_회원가입1);

        //then
        Member actual = memberJdbcServiceV2.findOneMember(expected.getId()).get();

        assertThat(actual.getId()).isEqualTo(expected.getId());

    }

    @Test
    @DisplayName("중복 닉네임 검사")
    void duplicateNicknameCheck(){
        //given
        memberJdbcServiceV2.join(멤버_회원가입1);
        //when
        try{
            memberJdbcServiceV2.join(new Member(null, "원진", "라이언"));
        }catch (IllegalStateException e){
            log.info(e.getMessage());
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 닉네임입니다");
        }

    }

    @Test
    @DisplayName("전체 회원 정보 조회")
    void findAllMember() {
        //given
        Member expected1 = memberJdbcServiceV2.join(멤버_회원가입1);
        Member expected2 = memberJdbcServiceV2.join(멤버_회원가입2);

        //when
        List<Member> actual = memberJdbcServiceV2.findAllMember();

        //then
        assertThat(actual.size()).isEqualTo(2);

        assertThat(expected1.getId()).isEqualTo(actual.get(0).getId());
        assertThat(expected2.getId()).isEqualTo(actual.get(1).getId());
    }

    @Test
    @DisplayName("회원 아이디로 정보 검색")
    void findOneMember() {
        //given
        Member expected = memberJdbcServiceV2.join(멤버_회원가입1);

        //when
        Optional<Member> actual = memberJdbcServiceV2.findOneMember(expected.getId());

        //then
        assertThat(actual.get().getId()).isEqualTo(expected.getId());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void deleteMember() {
        //given
        Member expected = memberJdbcServiceV2.join(멤버_회원가입1);

        //when
        memberJdbcServiceV2.deleteMember(expected.getId());

        //then
        assertThrows(NoSuchElementException.class,()->memberJdbcServiceV2.findOneMember(expected.getId()));
    }

}