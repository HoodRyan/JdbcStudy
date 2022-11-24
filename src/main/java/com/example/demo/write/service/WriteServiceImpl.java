package com.example.demo.write.service;

import com.example.demo.member.entity.Member;
import com.example.demo.write.repository.WriteRepository;
import com.example.demo.write.entity.Write;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WriteServiceImpl implements WriteService {

    private final WriteRepository writeRepository;

    @Autowired
    public WriteServiceImpl(WriteRepository writeRepository) {
        this.writeRepository = writeRepository;
    }


    @Override
    public Write create(Write write) {
        writeRepository.save(write);
        return write;
    }

    @Override
    public List<Write> findAllWrite() {
        return writeRepository.findAll();
    }

    @Override
    public Optional<Write> findOneWrite(Long writeId) {
        return writeRepository.findById(writeId);
    }

    @Override
    public void update(Long writeId, Write write) {
        writeRepository.update(writeId, write);
    }

    @Override
    public void delete(Long writeId) {
        writeRepository.delete(writeId);
    }
}
