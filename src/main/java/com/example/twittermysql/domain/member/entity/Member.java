package com.example.twittermysql.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Member {

    final private Long id;

    private String nickname;

    final private String email;

    final private LocalDate birthday;

    final private LocalDateTime createdAt;

    final private static Long NAME_MAX_LENGTH = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday,
            LocalDateTime createdAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);

        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH,
                "닉네임의 최대 길이는 " + NAME_MAX_LENGTH + "자 입니다.");
    }

    public void changeNickname(String to) {
        Objects.requireNonNull(to);
        validateNickname(to);
        nickname = to;
    }
}
