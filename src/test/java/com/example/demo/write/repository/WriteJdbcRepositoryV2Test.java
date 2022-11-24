package com.example.demo.write.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import com.example.demo.member.service.MemberJdbcServiceV2;
import com.example.demo.write.entity.Write;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WriteJdbcRepositoryV2Test {

    @Autowired
    WriteJdbcRepositoryV2 writeJdbcRepositoryV2;

    @Autowired
    MemberJdbcRepositoryV2 memberJdbcRepositoryV2;
    @Autowired
    MemberJdbcServiceV2 memberJdbcServiceV2;

    @BeforeEach
    void clear(){
        writeJdbcRepositoryV2.clear();
        memberJdbcRepositoryV2.clear();
    }

    @Test
    void save() {
        //given & when
        Member member = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));
        Write expected = writeJdbcRepositoryV2.save(new Write(null, "제목", "내용", member.getId()));

        Optional<Write> actual = writeJdbcRepositoryV2.findById(expected.getWriteId());
        //then
        assertThat(expected.getWriteId()).isEqualTo(actual.get().getWriteId());

    }

    @Test
    void findById() {
        //given
        Member member = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));
        Write expected = writeJdbcRepositoryV2.save(new Write(null, "제목", "내용", member.getId()));

        //when
        Optional<Write> actual = writeJdbcRepositoryV2.findById(expected.getWriteId());

        //then
        assertThat(expected.getWriteId()).isEqualTo(actual.get().getWriteId());

    }

    @Test
    void findAll() {
        //given
        Member member1 = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));
        Member member2 = memberJdbcServiceV2.join(new Member(null, "원진", "프로도"));
        Write expected1 = writeJdbcRepositoryV2.save(new Write(null, "제목1", "내용1", member1.getId()));
        Write expected2 = writeJdbcRepositoryV2.save(new Write(null, "제목2", "내용2", member2.getId()));

        //when
        List<Write> result = writeJdbcRepositoryV2.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);

        assertThat(expected1.getWriteId()).isEqualTo(result.get(0).getWriteId());
        assertThat(expected2.getWriteId()).isEqualTo(result.get(1).getWriteId());

    }

    @Test
    void update() {
        //given
        Member member = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));
        Write newWrite = new Write(null, "제목1", "내용1", member.getId());
        Write expected = writeJdbcRepositoryV2.save(newWrite);
        Long writeId = expected.getWriteId();

        //when
        Write updateWrite = new Write(null,"수정 제목","내용 수정", member.getId());
        writeJdbcRepositoryV2.update(writeId, updateWrite);

        Optional<Write> findWrite = writeJdbcRepositoryV2.findById(writeId);

        //then
        assertThat(findWrite.get().getTitle()).isEqualTo(updateWrite.getTitle());
        assertThat(findWrite.get().getContent()).isEqualTo(updateWrite.getContent());


    }

    @Test
    void delete() {
        //given
        Member member = memberJdbcServiceV2.join(new Member(null, "태용", "라이언"));
        Write newWrite = new Write(null, "제목1", "내용1", member.getId());
        Write expected = writeJdbcRepositoryV2.save(newWrite);

        //when
        writeJdbcRepositoryV2.delete(expected.getWriteId());

        //then
        assertThat(writeJdbcRepositoryV2.findById(expected.getWriteId())).isEmpty();
    }
}