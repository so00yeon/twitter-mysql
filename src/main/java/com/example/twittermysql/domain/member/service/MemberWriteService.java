package com.example.twittermysql.domain.member.service;

import com.example.twittermysql.domain.member.dto.RegisterMemberCommand;
import com.example.twittermysql.domain.member.entity.Member;
import com.example.twittermysql.domain.member.entity.MemberNicknameHistory;
import com.example.twittermysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.twittermysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

    final private MemberRepository memberRepository;

    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member register(RegisterMemberCommand command) {
        var member = Member.builder().nickname(command.nickname()).email(command.email())
                .birthday(command.birthday()).build();
        var savedMember = memberRepository.save(member);
        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long id, String nickname) {
        var member = memberRepository.findById(id).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder().memberId(member.getId()).nickname(
                member.getNickname()).build();

        memberNicknameHistoryRepository.save(history);
    }
}
