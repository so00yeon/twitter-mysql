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
        /*
          목표 - 회원정보(이메일, 닉네임, 생년월일)을 등록한다.
              - 닉네임은 10자를 넘길 수 없다.
          파라미터 - memberRegisterCommand

          var member = Member.of(memberRegisterCommand)
          memberRepository.save(member)
         */
        var member = Member.builder().nickname(command.nickname()).email(command.email())
                .birthday(command.birthday()).build();
        var savedMember = memberRepository.save(member);
        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long id, String nickname) {
        /*
        1. 회원의 이름을 변경
        2. 변경 내역을 저장한다.
         */
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
