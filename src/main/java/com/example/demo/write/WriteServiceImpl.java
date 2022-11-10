package com.example.demo.write;

import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WriteServiceImpl implements WriteService{

    private final WriteRepository writeRepository;

    @Autowired
    public WriteServiceImpl(WriteRepository writeRepository) {
        this.writeRepository = writeRepository;
    }


    @Override
    public void create(Write write) {
        writeRepository.save(write);
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
