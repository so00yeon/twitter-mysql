package com.example.twittermysql.domain.post.service;

import com.example.twittermysql.domain.post.dto.DailyPostCount;
import com.example.twittermysql.domain.post.dto.DailyPostCountRequest;
import com.example.twittermysql.domain.post.dto.PostDto;
import com.example.twittermysql.domain.post.entity.Post;
import com.example.twittermysql.domain.post.repository.PostLikeRepository;
import com.example.twittermysql.domain.post.repository.PostRepository;
import com.example.twittermysql.util.CursorRequest;
import com.example.twittermysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReadService {

    final private PostRepository postRepository;
    final private PostLikeRepository postLikeRepository;

    public PostDto toDto(Post post) {
        return new PostDto(post.getId(),
                post.getMemberId(),
                post.getContents(),
                postLikeRepository.count(post.getId()),
                post.getCreatedAt());
    }

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postRepository.groupByCreatedDate(request);
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable).map(this::toDto);
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);
        var nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = findAllBy(memberIds, cursorRequest);
        var nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByInId(ids);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId, false).orElseThrow();
    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(
                    cursorRequest.key(), memberId, cursorRequest.size());
        }
        return postRepository.findAllByMemberIdAndOrderByDesc(memberId, cursorRequest.size());
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndInMemberIdsAndOrderByIdDesc(
                    cursorRequest.key(), memberIds, cursorRequest.size());
        }
        return postRepository.findAllByInMemberIdsAndOrderByDesc(memberIds, cursorRequest.size());
    }

    private static long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }
}
