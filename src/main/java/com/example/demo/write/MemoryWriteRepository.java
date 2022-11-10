package com.example.demo.write;

import com.example.demo.member.Member;

import java.util.HashMap;
import java.util.Map;

public class MemoryWriteRepository implements WriteRepository{

    private static Map<String, Write> store = new HashMap<>();

    @Override
    public void save(Write write) {
        store.put(write.getTitle(),write);
        store.put(write.getContent(), write);
    }
}
