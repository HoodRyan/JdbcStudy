package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;



public class MemberJdbcRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> rowMapper = (rs, rowNum)->
            new Member(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("nickname")
            );

    public MemberJdbcRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name, nickname) values(?,?)";

        jdbcTemplate.update(sql, member.getName(), member.getNickname());
        return member;
    }



    @Override
    public Optional<Member> findById(Long memberId) {
        String sql = "select * from member where id = ?";
        try{
            Member member = jdbcTemplate.queryForObject(sql, rowMapper, memberId);
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        String sql = "select * from member where nickname = ?";
        try{
            Member member = jdbcTemplate.queryForObject(sql, rowMapper, nickname);
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public void delete(Long memberId) {
        String sql = "delete from member where id = ";
        jdbcTemplate.execute(sql+memberId);

    }

    @Override
    public void clear() {
        List<Member> all = findAll();
        for (Member member : all) {
            delete(member.getId());
        }
    }
}
