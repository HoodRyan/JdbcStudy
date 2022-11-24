package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.util.*;

public class MemberJdbcRepositoryV2 implements MemberRepository{

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public MemberJdbcRepositoryV2(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("MEMBER")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name","nickname");
    }


//    @Override
//    public Member save(Member member) {
//        String sql = "insert into Member (name, nickname) " +
//                "values (:name, :nickname)";
//        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        template.update(sql,param,keyHolder);
//        long key = keyHolder.getKey().longValue();
//        member.setId(key);
//        return member;
//    }

    @Override
    public Member save(Member member){
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        long id = jdbcInsert.executeAndReturnKey(param).longValue();
        return new Member(id, member.getName(), member.getNickname());
    }


    @Override
    public Optional<Member> findById(Long memberId) {
        String sql = "select * from Member where id = :id";
        try{
            Map<String, Object> param = Collections.singletonMap("id",memberId);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        String sql = "select * from Member where nickname = :nickname";
        try{
            Map<String, Object> param = Collections.singletonMap("nickname", nickname);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from Member";
        List<Member> find = template.query(sql, memberRowMapper());
        return find;
    }

    @Override
    public void delete(Long memberId) {
        String sql = "delete from Member where id = :id";

        template.update(sql, Collections.singletonMap("id",memberId));
    }

    @Override
    public void clear() {
        List<Member> all = findAll();
        for (Member member : all) {
            delete(member.getId());
        }
    }

//    private RowMapper<Member> memberRowMapper(){
//        return BeanPropertyRowMapper.newInstance(Member.class);
//    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member(rs.getLong("id"), rs.getString("name"), rs.getString("nickname"));
            return member;
        };
    }
    
    
}
