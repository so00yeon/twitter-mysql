package com.example.twittermysql.domain.post.service;

import com.example.twittermysql.domain.member.entity.Member;
import com.example.twittermysql.domain.post.entity.Post;
import com.example.twittermysql.domain.post.entity.PostLike;
import com.example.twittermysql.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeWriteService {

    final private PostLikeRepository postLikeRepository;

    public Long create(Post post, Member member) {
        var like = PostLike.builder()
                .postId(post.getId())
                .memberId(member.getId())
                .build();

        return postLikeRepository.save(like).getPostId();
    }
}
