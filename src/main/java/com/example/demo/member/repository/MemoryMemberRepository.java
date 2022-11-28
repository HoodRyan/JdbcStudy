package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member){

        if(member.getId() == null){
            long id = ++sequence;
            Member saveMember = new Member(id, member.getName(), member.getNickname());
            store.put(id,saveMember);
            return saveMember;
        }else{
            store.put(member.getId(), member);
        }

        return member;
    }


    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> duplicateNicknameCheck(String nickname) {
        return store.values() //해쉬맵의 컬렉션
                .stream() //컬렉션 순회
                .filter(member -> member.getNickname().equals(nickname)) //스트림에서 나오는 데이터 중 특정 값만 골라냄
                .findAny(); //조건에 일치하는 요소 1개 리턴
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public void clear() {
        List<Member> all = findAll();
        for (Member member : all) {
            delete(member.getId());
        }
    }

}
