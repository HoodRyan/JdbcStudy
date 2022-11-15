package com.example.demo.write;

import com.example.demo.member.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryWriteRepository implements WriteRepository{

    private static Map<Long, Write> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Write save(Write write){
        long id = ++sequence;
        store.put(id,new Write(id,write.getTitle(), write.getContent()));
        return write;
    }

    @Override
    public Write findById(Long writeId) {
        return store.get(writeId);
    }

    @Override
    public List<Write> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Long writeId, Write updateWrite) {
        Write findWrite = findById(writeId);
        findWrite.changeWrite(updateWrite);
    }

    @Override
    public void delete(Long writeId) {
        store.remove(writeId);
    }


}
