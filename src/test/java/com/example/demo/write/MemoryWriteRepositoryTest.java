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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoryWriteRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WriteRepository writeRepository;

    @Test
    void save(){
        //given
        Write write = new Write(0L,"제목","본문내용");
        //when
        Write save = writeRepository.save(write);

        //then
        assertThat(save.getWriteId()).isEqualTo(1L);

    }

    @Test
    void findById(){
        //given
        Write write = new Write(0L,"제목","본문내용");
        writeRepository.save(write);

        //when
        Write findWrite = writeRepository.findById(write.getWriteId());

        //then
        assertThat(findWrite).isEqualTo(write);
    }

    @Test
    void findAll(){
        //given
        Write write1 = new Write(0L,"제목1","내용1");
        Write write2 = new Write(1L,"제목2","내용2");

        writeRepository.save(write1);
        writeRepository.save(write2);

        //when
        List<Write> result = writeRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(write1,write2);
    }

    @Test
    void update(){
        //given
        Write write = new Write(0L,"제목1","내용1");
        Write savedWrite = writeRepository.save(write);
        Long writeId = savedWrite.getWriteId();

        //when
        Write updateWrite = new Write(0L,"수정 제목","내용 수정");
        writeRepository.update(writeId, updateWrite);

        Write findWrite = writeRepository.findById(writeId);

        //then
        assertThat(findWrite.getTitle()).isEqualTo(updateWrite.getTitle());
        assertThat(findWrite.getContent()).isEqualTo(updateWrite.getContent());

    }

    //모르겠다 어떻게 짜야할지.
    @Test
    void delete(){
        //given
        Write write = new Write(0L,"제목1","내용1");
        writeRepository.save(write);

        //when
        writeRepository.delete(write.getWriteId());

        //then



    }
}