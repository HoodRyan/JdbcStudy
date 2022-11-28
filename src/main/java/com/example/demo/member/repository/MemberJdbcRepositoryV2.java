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

    private static final String Member = "Member";

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public MemberJdbcRepositoryV2(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(Member)
                .usingGeneratedKeyColumns("id")
                .usingColumns("name","nickname");
    }

    /**
     * 멤버 저장
     * @param member
     * @return
     */
    @Override
    public Member save(Member member){
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        long id = jdbcInsert.executeAndReturnKey(param).longValue();
        return new Member(id, member.getName(), member.getNickname());
    }

    /**
     * 멤버 아이디로 검색
     * @param memberId
     * @return
     */
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

    /**
     * 닉네임으로 회원 정보 검색
     * @param nickname
     * @return
     */
    @Override
    public Optional<Member> duplicateNicknameCheck(String nickname) {
        String sql = "select * from Member where nickname = :nickname";
        try{
            Map<String, Object> param = Collections.singletonMap("nickname", nickname);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    /**
     * 전체 멤버 조회
     * @return
     */
    @Override
    public List<Member> findAll() {
        String sql = "select * from Member";
        List<Member> find = template.query(sql, memberRowMapper());
        return find;
    }

    /**
     * 멤버 삭제
     * @param memberId
     */
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


    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member(rs.getLong("id"), rs.getString("name"), rs.getString("nickname"));
            return member;
        };
    }
    
    
}
