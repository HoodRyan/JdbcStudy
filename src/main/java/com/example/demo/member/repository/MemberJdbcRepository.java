package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


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
        String sql = "insert into member_table(mb_name, mb_nickname) values(?,?)";
        jdbcTemplate.update(sql,member.getName(),member.getNickname());

        return member;
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        this.jdbcTemplate.update(connection ->{
//            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});    //자동 증가 키
//            ps.setString(1,member.getName());
//            ps.setString(2,member.getNickname());
//            return ps; }, keyHolder);
//
//        return new Member(keyHolder.getKey().longValue(),
//                member.getName(),
//                member.getNickname()
//        );
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        String sql = "select * from member_table where id = ?";
        try{
            Member member = jdbcTemplate.queryForObject(sql, rowMapper, memberId);
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        String sql = "select * from member_table where mb_nickname = ?";
        try{
            Member member = jdbcTemplate.queryForObject(sql, rowMapper, nickname);
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member_table";

        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public void delete(Long memberId) {
        String sql = "delete from member_table where id = ?";
        jdbcTemplate.queryForObject(sql, rowMapper, memberId);
    }

    @Override
    public void clear() {
        List<Member> all = findAll();
        for (Member member : all) {
            delete(member.getId());
        }
    }
}
