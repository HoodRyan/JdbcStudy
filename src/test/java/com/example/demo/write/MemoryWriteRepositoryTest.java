package com.example.demo.write;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemoryWriteRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WriteRepository writeRepository;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void clear(){
        writeRepository.deleteAll();
        memberRepository.clear();
    }

    @Test
    void save(){

        //given
        Member member = memberService.join(new Member(null,"태용","라이언"));
        Write expected = new Write(null,"제목","본문내용",member.getId());
        //when
        Write save = writeRepository.save(expected, member);
        Write actual = writeRepository.findById(save.getWriteId()).get();

        //then
        assertThat(actual.getWriteId()).isEqualTo(save.getWriteId());
    }

    @Test
    void findById(){
        //given
        Member member = memberService.join(new Member(null,"태용","라이언"));
        Write write = new Write(null,"제목","본문내용",member.getId());
        Write save = writeRepository.save(write, member);

        //when
        Optional<Write> findWrite = writeRepository.findById(save.getWriteId());

        //then
        assertThat(findWrite.get().getWriteId()).isEqualTo(save.getWriteId());

    }

    @Test
    void findAll(){
        //given
        Member member = memberService.join(new Member(null,"태용","라이언"));
        Write write1 = new Write(null,"제목1","내용1",member.getId());
        Write write2 = new Write(null,"제목2","내용2",member.getId());

        Write save1 = writeRepository.save(write1, member);
        Write save2 = writeRepository.save(write2, member);

        //when
        List<Write> result = writeRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getWriteId()).isEqualTo(save1.getWriteId());
        assertThat(result.get(1).getWriteId()).isEqualTo(save2.getWriteId());
    }

    @Test
    void update(){
        //given
        Member member = memberService.join(new Member(null,"태용","라이언"));
        Write write = new Write(null,"제목1","내용1",member.getId());
        Write savedWrite = writeRepository.save(write,member);
        Long writeId = savedWrite.getWriteId();

        //when
        Write updateWrite = new Write(null,"수정 제목","내용 수정", member.getId());
        writeRepository.update(writeId, updateWrite);

        Optional<Write> findWrite = writeRepository.findById(writeId);

        //then
        assertThat(findWrite.get().getTitle()).isEqualTo(updateWrite.getTitle());
        assertThat(findWrite.get().getContent()).isEqualTo(updateWrite.getContent());

    }


    @Test
    void delete(){

        //given
        Member member = memberService.join(new Member(null,"태용","라이언"));
        Write expected = new Write(null,"제목","본문내용",member.getId());
        writeRepository.save(expected,member);

        //when
        writeRepository.delete(expected.getWriteId());

        //then
        assertThat(writeRepository.findById(expected.getWriteId())).isEmpty();

    }
}