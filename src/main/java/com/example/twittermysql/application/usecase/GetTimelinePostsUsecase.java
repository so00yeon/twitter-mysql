package com.example.twittermysql.application.usecase;

import com.example.twittermysql.domain.follow.entity.Follow;
import com.example.twittermysql.domain.follow.service.FollowReadService;
import com.example.twittermysql.domain.post.entity.Post;
import com.example.twittermysql.domain.post.service.PostReadService;
import com.example.twittermysql.util.CursorRequest;
import com.example.twittermysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTimelinePostsUsecase {

    final private FollowReadService followReadService;
    final private PostReadService postReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }
}
