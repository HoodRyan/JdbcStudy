package com.example.demo.write;

import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoryWriteRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WriteRepository writeRepository;

    @Autowired
    MemberService memberService;

    @Test
    void save(){

        //given
        Member member = memberService.join(new Member(1L,"태용","라이언"));
        Write expected = new Write(1L,"제목","본문내용",member.getId());
        //when
        writeRepository.save(expected,member);
        Write actual = writeRepository.findById(expected.getWriteId()).get();

        //then
        assertThat(actual.getWriteId()).isEqualTo(expected.getWriteId());
    }

    @Test
    void findById(){
        //given
        Member member = memberService.join(new Member(0L,"태용","라이언"));
        Write write = new Write(0L,"제목","본문내용",member.getId());
        writeRepository.save(write,member);

        //when
        Optional<Write> findWrite = writeRepository.findById(write.getWriteId());

        //then
        assertThat(findWrite.get().getWriteId()).isEqualTo(write.getWriteId());
    }

    @Test
    void findAll(){
        //given
        Member member = memberService.join(new Member(0L,"태용","라이언"));
        Write write1 = new Write(0L,"제목1","내용1",member.getId());
        Write write2 = new Write(1L,"제목2","내용2",member.getId());

        writeRepository.save(write1,member);
        writeRepository.save(write2,member);

        //when
        List<Write> result = writeRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(write1,write2);
    }

    @Test
    void update(){
        //given
        Member member = memberService.join(new Member(0L,"태용","라이언"));
        Write write = new Write(0L,"제목1","내용1",member.getId());
        Write savedWrite = writeRepository.save(write,member);
        Long writeId = savedWrite.getWriteId();

        //when
        Write updateWrite = new Write(0L,"수정 제목","내용 수정", member.getId());
        writeRepository.update(writeId, updateWrite);

        Optional<Write> findWrite = writeRepository.findById(writeId);

        //then
        assertThat(findWrite.get().getTitle()).isEqualTo(updateWrite.getTitle());
        assertThat(findWrite.get().getContent()).isEqualTo(updateWrite.getContent());

    }


    @Test
    void delete(){

        //given
        Member member = memberService.join(new Member(1L,"태용","라이언"));
        Write expected = new Write(1L,"제목","본문내용",member.getId());
        writeRepository.save(expected,member);

        //when
        writeRepository.delete(expected.getWriteId());

        //then
        assertThat(writeRepository.findById(expected.getWriteId())).isEmpty();

    }
}