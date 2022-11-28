package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
        Member member = memberJdbcRepositoryV2.save(new Member(null, "태용", "라이언"));

        //then
        Optional<Member> actual = memberJdbcRepositoryV2.findById(member.getId());

        assertThat(actual.get().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("멤버 아이디로 정보 검색")
    void findById() {
        //given
        Member expected = memberJdbcRepositoryV2.save(new Member(null, "태용", "라이언"));

        //when
        Member actual = memberJdbcRepositoryV2.findById(expected.getId()).get();

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    @DisplayName("멤버 닉네임으로 정보 검색")
    void findByNickname() {
        //given
        Member expected = memberJdbcRepositoryV2.save(new Member(null, "태용", "라이언"));

        //when
        Member actual = memberJdbcRepositoryV2.duplicateNicknameCheck(expected.getNickname()).get();


        //then
        assertThat(actual.getNickname()).isEqualTo(expected.getNickname());
    }

    @Test
    @DisplayName("전체 멤버 정보 조회")
    void findAll() {
        //given
        Member save1 = memberJdbcRepositoryV2.save(new Member(null, "태용", "라이언"));
        Member save2 = memberJdbcRepositoryV2.save(new Member(null, "원진", "프로도"));

        //when
        List<Member> result = memberJdbcRepositoryV2.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);

        assertThat(save1.getId()).isEqualTo(result.get(0).getId());
        assertThat(save2.getId()).isEqualTo(result.get(1).getId());

        assertThat(save1.getName()).isEqualTo(result.get(0).getName());
        assertThat(save2.getName()).isEqualTo(result.get(1).getName());

        assertThat(save1.getNickname()).isEqualTo(result.get(0).getNickname());
        assertThat(save2.getNickname()).isEqualTo(result.get(1).getNickname());

    }

    @Test
    @DisplayName("멤버 정보 삭제")
    void delete() {
        //given
        Member expected1 = memberJdbcRepositoryV2.save(new Member(null, "태용", "라이언"));

        //when
        memberJdbcRepositoryV2.delete(expected1.getId());

        //then
        assertThat(memberJdbcRepositoryV2.findById(expected1.getId())).isEmpty();
    }
}