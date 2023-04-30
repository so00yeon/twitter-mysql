package com.example.twittermysql.domain.follow.service;

import com.example.twittermysql.domain.follow.entity.Follow;
import com.example.twittermysql.domain.follow.repository.FollowRepository;
import com.example.twittermysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class FollowWriteService {

    final private FollowRepository followRepository;

    public void create(Member fromMember, Member toMember) {
        Assert.isTrue(!fromMember.getId().equals(toMember.getId()), "From, To 회원이 동일합니다.");

        var follow = Follow.builder().fromMemberId(fromMember.getId()).toMemberId(toMember.getId())
                .build();

        followRepository.save(follow);
    }
}
