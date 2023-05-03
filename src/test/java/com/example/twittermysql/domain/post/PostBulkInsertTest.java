package com.example.twittermysql.domain.post;

import com.example.twittermysql.domain.post.entity.Post;
import com.example.twittermysql.domain.post.repository.PostRepository;
import com.example.twittermysql.util.PostFixtureFactory;
import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 2, 1)
        );

        var posts = IntStream.range(0, 10)
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        postRepository.bulkInsert(posts);
    }
}
