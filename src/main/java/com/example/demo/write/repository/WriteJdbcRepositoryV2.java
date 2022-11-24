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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository
public class WriteJdbcRepositoryV2 implements WriteRepository{

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public WriteJdbcRepositoryV2(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Write")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Write save(Write write) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(write);
        long id = jdbcInsert.executeAndReturnKey(param).longValue();

        return new Write(id, write.getTitle(), write.getContent(), write.getMember_id());
    }

    @Override
    public Optional<Write> findById(Long writeId) {
        String sql = "select * from Write where id = :id";
        try{
            Map<String, Object> param = Collections.singletonMap("id",writeId);
            Write write = template.queryForObject(sql, param, writeRowMapper());
            return Optional.of(write);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Write> findAll() {
        String sql = "select * from Write";
        List<Write> find = template.query(sql, writeRowMapper());
        return find;
    }

    @Override
    public void update(Long writeId, Write write) {
        String sql = "update Write set title = :title, content = :content where id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", write.getTitle())
                .addValue("content", write.getContent())
                .addValue("id", writeId);
        template.update(sql,param);
    }

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
