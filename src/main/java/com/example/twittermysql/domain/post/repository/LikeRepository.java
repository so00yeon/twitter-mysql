package com.example.twittermysql.domain.post.repository;

import com.example.twittermysql.domain.post.entity.Like;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LikeRepository {

    final static String TABLE = "Like";
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    final static private RowMapper<Like> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Like.builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .postId(resultSet.getLong("postId"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public Like save(Like like) {
        if (like.getId() == null) {
            return insert(like);
        }

        throw new UnsupportedOperationException("Like는 갱신을 지원하지 않습니다.");
    }

    private Like insert(Like like) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
                namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(like);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Like.builder()
                .id(id)
                .memberId(like.getMemberId())
                .postId(like.getPostId())
                .createdAt(like.getCreatedAt())
                .build();
    }
}
