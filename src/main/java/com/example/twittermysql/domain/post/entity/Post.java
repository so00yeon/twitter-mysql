package com.example.twittermysql.domain.post.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    final private Long id;
    final private Long memberId;
    final private String contents;
    final private LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
