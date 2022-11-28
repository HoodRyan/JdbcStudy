package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.demo.common.MemberFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class MemberJdbcRepositoryV2Test {


    @Autowired
    MemberJdbcRepositoryV2 memberJdbcRepositoryV2;

    @BeforeEach
    void clear(){
        memberJdbcRepositoryV2.clear();
    }



    @Test
    @DisplayName("멤버 정보 저장")
    void save() {
        //given & when
        Member member = memberJdbcRepositoryV2.save(멤버_회원가입1);
//        Member member = memberJdbcRepositoryV2.save(new Member(null, "태용", "라이언"));

        //then
        Member actual = memberJdbcRepositoryV2.findById(member.getId()).get();

        assertThat(actual.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("멤버 아이디로 정보 검색")
    void findById() {
        //given
        Member expected = memberJdbcRepositoryV2.save(멤버_회원가입1);

        //when
        Member actual = memberJdbcRepositoryV2.findById(expected.getId()).get();

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    @DisplayName("멤버 닉네임으로 정보 검색")
    void findByNickname() {
        //given
        Member expected = memberJdbcRepositoryV2.save(멤버_회원가입1);

        //when
        Member actual = memberJdbcRepositoryV2.duplicateNicknameCheck(expected.getNickname()).get();

        //then
        assertThat(actual.getNickname()).isEqualTo(expected.getNickname());
    }

    @Test
    @DisplayName("전체 멤버 정보 조회")
    void findAll() {
        //given
        Member save1 = memberJdbcRepositoryV2.save(멤버_회원가입1);
        Member save2 = memberJdbcRepositoryV2.save(멤버_회원가입2);

        //when
        List<Member> result = memberJdbcRepositoryV2.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);

        assertThat(save1.getId()).isEqualTo(result.get(0).getId());
        assertThat(save2.getId()).isEqualTo(result.get(1).getId());
    }

    @Test
    @DisplayName("멤버 정보 삭제")
    void delete() {
        //given
        Member expected1 = memberJdbcRepositoryV2.save(멤버_회원가입1);

        //when
        memberJdbcRepositoryV2.delete(expected1.getId());

        //then
        assertThat(memberJdbcRepositoryV2.findById(expected1.getId())).isEmpty();
//        assertThrows(EmptyResultDataAccessException.class,()->memberJdbcRepositoryV2.findById(expected1.getId()));
    }
}