package com.example.twittermysql.domain.member.service;

import com.example.twittermysql.domain.member.dto.MemberDto;
import com.example.twittermysql.domain.member.entity.Member;
import com.example.twittermysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReadService {

    final private MemberRepository memberRepository;

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(),
                member.getBirthday());
    }
}
