package com.example.twittermysql.application.controller;

import com.example.twittermysql.application.usecase.CreateFollowUsecase;
import com.example.twittermysql.application.usecase.GetFollowingMembersUsecase;
import com.example.twittermysql.domain.member.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    final private CreateFollowUsecase createFollowUsecase;
    final private GetFollowingMembersUsecase getFollowingMembersUsecase;

    @PostMapping("/{fromId}/{toId}")
    public void create(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowUsecase.execute(fromId, toId);
    }

    @GetMapping("/members/{fromId}")
    public List<MemberDto> getFollowers(@PathVariable Long fromId) {
        return getFollowingMembersUsecase.execute(fromId);
    }
}
