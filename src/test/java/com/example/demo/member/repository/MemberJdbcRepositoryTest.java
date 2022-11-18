package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        Member expected = memberJdbcRepository.save(new Member(null, "태용", "라이언"));

        //then
        Optional<Member> actual = memberJdbcRepository.findById(expected.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findById() {
    }

    @Test
    void findByNickname() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

}