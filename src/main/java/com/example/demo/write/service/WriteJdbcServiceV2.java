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

    @Override
    public Write create(Write write) {
        return writeJdbcRepositoryV2.save(write);
    }

    @Override
    public List<Write> findAllWrite() {
        return writeJdbcRepositoryV2.findAll();
    }

    @Override
    public Optional<Write> findOneWrite(Long writeId) {
        Optional<Write> byId = writeJdbcRepositoryV2.findById(writeId);
        if(byId.isEmpty()){
            throw new NoSuchElementException("값이 없다");
        }
        return byId;
    }

    @Override
    public void update(Long writeId, Write write) {
        writeJdbcRepositoryV2.update(writeId, write);
    }

    @Override
    public void delete(Long writeId) {
        writeJdbcRepositoryV2.delete(writeId);
    }
}
