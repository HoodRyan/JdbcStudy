package com.example.demo.write.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.write.entity.Write;

import java.util.*;

public class MemoryWriteRepository implements WriteRepository {

    private static Map<Long, Write> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Write save(Write write){

        if(write.getWriteId() == null){
            long id = ++sequence;
            Write saveWrite = new Write(id, write.getTitle(), write.getContent(), write.getMember_id());
            store.put(id,saveWrite);
            return saveWrite;
        }else{
            store.put(write.getWriteId(), write);
        }
        return write;
    }


    @Override
    public Optional<Write> findById(Long writeId) {
        return Optional.ofNullable(store.get(writeId));
    }

    @Override
    public List<Write> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Long writeId, Write updateWrite) {
        Optional<Write> findWrite = findById(writeId);
        findWrite.get().changeWrite(updateWrite);
    }

    @Override
    public void delete(Long writeId) {
        store.remove(writeId);
    }

    @Override
    public void clear() {
        List<Write> all = findAll();
        for (Write write : all) {
            delete(write.getWriteId());
        }
    }


}
