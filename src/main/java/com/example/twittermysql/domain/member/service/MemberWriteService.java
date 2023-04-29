package com.example.twittermysql.domain.member.service;

import com.example.twittermysql.domain.member.dto.RegisterMemberCommand;
import com.example.twittermysql.domain.member.entity.Member;
import com.example.twittermysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

    final private MemberRepository memberRepository;

    public Member create(RegisterMemberCommand command) {
        /*
          목표 - 회원정보(이메일, 닉네임, 생년월일)을 등록한다.
              - 닉네임은 10자를 넘길 수 없다.
          파라미터 - memberRegisterCommand

          var member = Member.of(memberRegisterCommand)
          memberRepository.save(member)
         */
        var member = Member.builder().nickname(command.nickname()).email(command.email())
                .birthday(command.birthday()).build();
        return memberRepository.save(member);


    }
}
