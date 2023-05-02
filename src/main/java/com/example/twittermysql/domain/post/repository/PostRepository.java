package com.example.twittermysql.domain.post.repository;

import com.example.twittermysql.domain.post.dto.DailyPostCount;
import com.example.twittermysql.domain.post.dto.DailyPostCountRequest;
import com.example.twittermysql.domain.post.entity.Post;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    final static String TABLE = "Post";

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    final static private RowMapper<DailyPostCount> DAILY_POST_COUNT_MAPPER = (ResultSet resultSet, int rowNum) -> new DailyPostCount(
            resultSet.getLong("memberId"),
            resultSet.getObject("createdDate", LocalDate.class),
            resultSet.getLong("count")
    );

    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        String CREATE_DATE = "DATE_FORMAT(createdAt,'%Y-%m-%d')";
        var sql = String.format("""
                SELECT %s createdDate, memberId, count(id) as count
                FROM %s
                WHERE memberId = :memberId and createdAt between :firstDate and :lastDate
                GROUP BY memberId, createdDate
                """, CREATE_DATE, TABLE);
        var params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_MAPPER);
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        }
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    public Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
                namedParameterJdbcTemplate.getJdbcTemplate()).withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdAt(post.getCreatedAt()).build();
    }
}
