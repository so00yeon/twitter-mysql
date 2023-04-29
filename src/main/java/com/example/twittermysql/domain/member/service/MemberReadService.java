package com.example.twittermysql.domain.member.service;

import com.example.twittermysql.domain.member.dto.MemberDto;
import com.example.twittermysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.twittermysql.domain.member.entity.Member;
import com.example.twittermysql.domain.member.entity.MemberNicknameHistory;
import com.example.twittermysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.twittermysql.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReadService {

    final private MemberRepository memberRepository;
    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryRepository.findAllByMemberId(memberId).stream().map(this::toDto)
                .toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(),
                member.getBirthday());
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(history.getId(), history.getMemberId(),
                history.getNickname(), history.getCreatedAt());
    }
}
