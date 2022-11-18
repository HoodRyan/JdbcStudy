package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberJdbcRepositoryTest {

    @Autowired
    MemberJdbcRepository memberJdbcRepository;

    @BeforeEach
    void clear(){
        memberJdbcRepository.clear();
    }

    @Test
    void save() {
        //given & when
        Member member = memberJdbcRepository.save(new Member(null, "태용", "라이언"));

        //then
        Optional<Member> actual = memberJdbcRepository.findById(member.getId());
        assertThat(actual.get().getId()).isEqualTo(member.getId());
    }

    @Test
    void findById() {
        //given
        Member expected = memberJdbcRepository.save(new Member(null, "태용", "라이언"));

        System.out.println(expected.getId());
        //when
        Member actual = memberJdbcRepository.findById(expected.getId()).get();

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void findByNickname() {
        //given
        Member expected = memberJdbcRepository.save(new Member(null, "태용", "라이언"));
        //when
        Member actual = memberJdbcRepository.findByNickname(expected.getNickname()).get();
        //then
        assertThat(actual.getNickname()).isEqualTo(expected.getNickname());
    }

    @Test
    void findAll() {
        //given
        Member save1 = memberJdbcRepository.save(new Member(null, "태용", "라이언"));
        Member save2 = memberJdbcRepository.save(new Member(null, "원진", "프로도"));

        System.out.println(save1.getId());
        System.out.println(save1.getName());
        System.out.println(save1.getNickname());

        System.out.println(save2.getId());
        System.out.println(save2.getName());
        System.out.println(save2.getNickname());

        //when
        List<Member> result = memberJdbcRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void delete() {
        //given
        Member expected1 = memberJdbcRepository.save(new Member(null, "태용", "라이언"));

        //when
        memberJdbcRepository.delete(expected1.getId());

        //then
        assertThat(memberJdbcRepository.findById(expected1.getId())).isEmpty();
    }

}