package com.example.demo.write.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import com.example.demo.write.entity.Write;
import com.example.demo.write.repository.WriteJdbcRepositoryV2;
import com.example.demo.write.repository.WriteRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class WriteJdbcServiceV2 implements WriteService{

    private final WriteJdbcRepositoryV2 writeJdbcRepositoryV2;
    public WriteJdbcServiceV2(WriteJdbcRepositoryV2 writeJdbcRepositoryV2) {
        this.writeJdbcRepositoryV2 = writeJdbcRepositoryV2;
    }

    /**
     * 게시글 작성
     * @param write
     * @return
     */
    @Override
    public Write create(Write write) {
        return writeJdbcRepositoryV2.save(write);
    }

    /**
     * 게시글 전체 조회
     * @return
     */
    @Override
    public List<Write> findAllWrite() {
        return writeJdbcRepositoryV2.findAll();
    }

    /**
     * 게시글 키워드(제목) 검색
     * @param title
     * @return
     */
    @Override
    public List<Write> searchTitle(String title) {
        return writeJdbcRepositoryV2.findByTitle(title);
    }

    /**
     * 게시글 조회
     * @param writeId
     * @return
     */
    @Override
    public Optional<Write> findOneWrite(Long writeId) {
        Optional<Write> byId = writeJdbcRepositoryV2.findById(writeId);
        if(byId.isEmpty()){
            throw new NoSuchElementException("값이 없다");
        }
        return byId;
    }

    /**
     * 게시글 수정
     * @param writeId
     * @param write
     */
    @Override
    public void update(Long writeId, Write write) {
        writeJdbcRepositoryV2.update(writeId, write);
    }

    /**
     * 게시글 삭제
     * @param writeId
     */
    @Override
    public void delete(Long writeId) {
        writeJdbcRepositoryV2.delete(writeId);
    }
}
