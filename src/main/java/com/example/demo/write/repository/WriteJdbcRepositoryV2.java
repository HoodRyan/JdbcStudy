package com.example.demo.write.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.write.entity.Write;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;


public class WriteJdbcRepositoryV2 implements WriteRepository{

    private static final String Write = "Write";
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public WriteJdbcRepositoryV2(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(Write)
                .usingGeneratedKeyColumns("id");
    }

    /**
     * 게시글 저장
     * @param write
     * @return
     */
    @Override
    public Write save(Write write) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(write);
        long id = jdbcInsert.executeAndReturnKey(param).longValue();

        return new Write(id, write.getTitle(), write.getContent(), write.getMember_id());
    }

    /**
     * 게시글 아이디로 검색
     * @param writeId
     * @return
     */
    @Override
    public Optional<Write> findById(Long writeId) {
        String sql = "select * from Write where id = :id";
        Map<String, Object> param = Collections.singletonMap("id",writeId);
        Write write = template.queryForObject(sql, param, writeRowMapper());
        return Optional.ofNullable(write);

    }

    /**
     * 게시글 키워드(제목)으로 검색
     * @param title
     * @return
     */
    @Override
    public List<Write> findByTitle(String title) {
        String sql = "select * from Write where title like '%'||:title||'%'";
        Map<String, Object> param = Collections.singletonMap("title",title);
        return template.query(sql, param, writeRowMapper());
    }

    /**
     * 게시글 전체 조회
     * @return
     */
    @Override
    public List<Write> findAll() {
        String sql = "select * from Write";
        return template.query(sql, writeRowMapper());
    }

    /**
     * 게시글 수정
     * @param writeId
     * @param write
     */
    @Override
    public void update(Long writeId, Write write) {
        String sql = "update Write set title = :title, content = :content where id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", write.getTitle())
                .addValue("content", write.getContent())
                .addValue("id", writeId);
        template.update(sql,param);
    }

    /**
     * 게시글 삭제
     * @param writeId
     */
    @Override
    public void delete(Long writeId) {
        String sql = "delete from Write where id = :id";

        template.update(sql, Collections.singletonMap("id",writeId));
    }

    @Override
    public void clear() {
        List<Write> all = findAll();
        for (Write write : all) {
            delete(write.getWriteId());
        }
    }

    private RowMapper<Write> writeRowMapper(){
        return (rs, rowNum) -> {
            Write write = new Write(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getLong("member_id"));
            return write;
        };
    }
}
