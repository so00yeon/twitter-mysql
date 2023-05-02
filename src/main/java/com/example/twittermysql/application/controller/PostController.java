package com.example.twittermysql.application.controller;

import com.example.twittermysql.domain.post.dto.DailyPostCount;
import com.example.twittermysql.domain.post.dto.DailyPostCountRequest;
import com.example.twittermysql.domain.post.dto.PostCommand;
import com.example.twittermysql.domain.post.service.PostReadService;
import com.example.twittermysql.domain.post.service.PostWriteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    final private PostWriteService postWriteService;
    final private PostReadService postReadService;

    @PostMapping()
    public Long create(PostCommand command) {
        return postWriteService.create(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCount(@RequestBody DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }
}
