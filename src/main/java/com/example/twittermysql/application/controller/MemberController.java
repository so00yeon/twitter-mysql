package com.example.twittermysql.application.controller;

import com.example.twittermysql.domain.member.dto.MemberDto;
import com.example.twittermysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.twittermysql.domain.member.dto.RegisterMemberCommand;
import com.example.twittermysql.domain.member.service.MemberReadService;
import com.example.twittermysql.domain.member.service.MemberWriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    final private MemberWriteService memberWriteService;
    final private MemberReadService memberReadService;

    @PostMapping()
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        var member = memberWriteService.register(command);
        return memberReadService.toDto(member);
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        var member = memberReadService
                .getMember(id);
        return memberReadService.toDto(member);
    }

    @PostMapping("/{id}/name")
    public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        memberWriteService.changeNickname(id, nickname);
        var member = memberReadService
                .getMember(id);
        return memberReadService.toDto(member);
    }

    @GetMapping("/{memberId}/nickname-histories")
    public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long memberId) {
        return memberReadService.getNicknameHistories(memberId);
    }
}
