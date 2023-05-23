package com.example.twittermysql.domain.post.service;

import com.example.twittermysql.domain.member.entity.Member;
import com.example.twittermysql.domain.post.entity.Like;
import com.example.twittermysql.domain.post.entity.Post;
import com.example.twittermysql.domain.post.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeWriteService {

    final private LikeRepository likeRepository;

    public Long create(Post post, Member member) {
        var like = Like.builder()
                .postId(post.getId())
                .memberId(member.getId())
                .build();

        return likeRepository.save(like).getPostId();
    }
}
