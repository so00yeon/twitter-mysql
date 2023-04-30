package com.example.twittermysql.domain.follow.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Follow {

    final private Long id;

    final private Long fromMemberId;

    final private Long toMemberId;

    final private LocalDateTime createdAt;

    @Builder
    public Follow(Long id, Long fromMemberId, Long toMemberId, LocalDateTime createdAt) {
        this.id = id;
        this.fromMemberId = Objects.requireNonNull(fromMemberId);
        this.toMemberId = Objects.requireNonNull(toMemberId);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
