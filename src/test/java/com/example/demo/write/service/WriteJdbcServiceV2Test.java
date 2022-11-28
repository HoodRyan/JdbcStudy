package com.example.demo.write.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import com.example.demo.member.service.MemberJdbcServiceV2;
import com.example.demo.write.entity.Write;
import com.example.demo.write.repository.WriteJdbcRepositoryV2;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.demo.common.MemberFixtures.멤버_회원가입1;
import static com.example.demo.common.MemberFixtures.멤버_회원가입2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class WriteJdbcServiceV2Test {

    @Autowired
    WriteJdbcRepositoryV2 writeJdbcRepositoryV2;
    @Autowired
    WriteJdbcServiceV2 writeJdbcServiceV2;
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
    @DisplayName("게시글 작성")
    void create() {
        //given & when
        Member member = memberJdbcServiceV2.join(멤버_회원가입1);
        Write expected = writeJdbcServiceV2.create(new Write(null, "제목", "내용", member.getId()));

        Optional<Write> actual = writeJdbcServiceV2.findOneWrite(expected.getWriteId());

        //then
        assertThat(expected.getWriteId()).isEqualTo(actual.get().getWriteId());

    }

    @Test
    @DisplayName("제목(키워드) 단어로 검색")
    void searchTitle(){
        //given
        Member member = memberJdbcServiceV2.join(멤버_회원가입1);
        Member member2 = memberJdbcServiceV2.join(멤버_회원가입2);
        Write expected1 = writeJdbcServiceV2.create(new Write(null, "제목123", "내용", member.getId()));
        Write expected2 = writeJdbcServiceV2.create(new Write(null, "ㅂㅈㄷㄱ", "ㅋㅋ", member.getId()));
        Write expected3 = writeJdbcServiceV2.create(new Write(null, "제목789", "흠..", member2.getId()));
        Write expected4 = writeJdbcServiceV2.create(new Write(null, "ㅁㄴㅇㄹ", "내용", member2.getId()));

        //when
        List<Write> actual = writeJdbcServiceV2.searchTitle("제목");

        //then
        assertThat(actual.get(0).getWriteId()).isEqualTo(expected1.getWriteId());
        assertThat(actual.get(1).getWriteId()).isEqualTo(expected3.getWriteId());
        assertThat(actual.get(0).getTitle()).isEqualTo(expected1.getTitle());

        for(int i=0;i<actual.size();i++){
            log.info(actual.get(i).getTitle() +" "+ actual.get(i).getContent() +" "+ actual.get(i).getMember_id());
        }
    }



    @Test
    @DisplayName("게시글 전체 불러오기")
    void findAllWrite() {
        //given
        Member member1 = memberJdbcServiceV2.join(멤버_회원가입1);
        Member member2 = memberJdbcServiceV2.join(멤버_회원가입2);
        Write expected1 = writeJdbcServiceV2.create(new Write(null, "제목1", "내용1", member1.getId()));
        Write expected2 = writeJdbcServiceV2.create(new Write(null, "제목2", "내용2", member2.getId()));

        //when
        List<Write> actual = writeJdbcServiceV2.findAllWrite();

        //then
        assertThat(actual.size()).isEqualTo(2);

        assertThat(expected1.getWriteId()).isEqualTo(actual.get(0).getWriteId());
        assertThat(expected2.getWriteId()).isEqualTo(actual.get(1).getWriteId());

    }

    @Test
    @DisplayName("게시글 아이디로 정보 불러오기")
    void findOneWrite() {
        //given
        Member member = memberJdbcServiceV2.join(멤버_회원가입1);
        Write expected = writeJdbcServiceV2.create(new Write(null, "제목", "내용", member.getId()));

        //when
        Optional<Write> actual = writeJdbcServiceV2.findOneWrite(expected.getWriteId());

        //then
        assertThat(actual.get().getWriteId()).isEqualTo(expected.getWriteId());
    }

    @Test
    @DisplayName("게시글 수정")
    void update() {
        //given
        Member member = memberJdbcServiceV2.join(멤버_회원가입1);
        Write beforeWrite = new Write(null, "수정 전 제목", "수정 전 내용", member.getId());
        Write expected = writeJdbcServiceV2.create(beforeWrite);
        Long writeId = expected.getWriteId();

        //when
        Write afterWrite = new Write(null, "수정 후 제목", "수정 후 내용", member.getId());
        writeJdbcServiceV2.update(writeId, afterWrite);

        Optional<Write> actual = writeJdbcServiceV2.findOneWrite(writeId);

        //then
        assertThat(actual.get().getTitle()).isEqualTo(afterWrite.getTitle());
        assertThat(actual.get().getContent()).isEqualTo(afterWrite.getContent());

    }

    @Test
    @DisplayName("게시글 삭제")
    void delete() {
        //given
        Member member = memberJdbcServiceV2.join(멤버_회원가입1);
        Write write = new Write(null, "수정 전 제목", "수정 전 내용", member.getId());
        Write expected = writeJdbcServiceV2.create(write);

        //when
        writeJdbcServiceV2.delete(expected.getWriteId());

        //then
        assertThrows(EmptyResultDataAccessException.class,()->writeJdbcServiceV2.findOneWrite(expected.getWriteId()));
    }
}