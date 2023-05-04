package com.example.twittermysql.domain.post.service;

import com.example.twittermysql.domain.post.dto.DailyPostCount;
import com.example.twittermysql.domain.post.dto.DailyPostCountRequest;
import com.example.twittermysql.domain.post.entity.Post;
import com.example.twittermysql.domain.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReadService {

    final private PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postRepository.groupByCreatedDate(request);
    }

    public Page<Post> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable);
    }
}
