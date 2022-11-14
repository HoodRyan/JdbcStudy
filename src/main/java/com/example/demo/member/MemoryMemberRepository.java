package com.example.demo.member;

import com.example.demo.write.Write;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member){
//        member.setId(++sequence);
//        store.put(member.getId(),member);
        long id = ++sequence;
        store.put(id,new Member(id,member.getName(), member.getNickname()));

        return member;
    }


    @Override
    public Member findById(Long id) {
        return store.get(id);
    }

    @Override
    public Member findByNickname(String nickname) {
        return null;
    }

    @Override
    public void delete(Long writeId) {
        store.remove(writeId);
    }


}
