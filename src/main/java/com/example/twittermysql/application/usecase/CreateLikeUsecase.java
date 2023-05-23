package com.example.twittermysql.application.usecase;

import com.example.twittermysql.domain.member.service.MemberReadService;
import com.example.twittermysql.domain.post.service.LikeWriteService;
import com.example.twittermysql.domain.post.service.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateLikeUsecase {

    final private PostReadService postReadService;
    final private MemberReadService memberReadService;
    final private LikeWriteService likeWriteService;

    public void execute(Long postId, Long memberId) {
        var post = postReadService.getPost(postId);
        var member = memberReadService.getMember(memberId);
        likeWriteService.create(post, member);
    }
}
