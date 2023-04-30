package com.example.twittermysql.application.usecase;

import com.example.twittermysql.domain.follow.entity.Follow;
import com.example.twittermysql.domain.follow.service.FollowReadService;
import com.example.twittermysql.domain.member.dto.MemberDto;
import com.example.twittermysql.domain.member.service.MemberReadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetFollowingMembersUsecase {

    final private MemberReadService memberReadService;
    final private FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        /*
            1. fromMemberId = memberId -> Follow list
            2. 1번을 순회하면서 회원정보를 찾는다.
         */
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMemberIds);
    }
}
