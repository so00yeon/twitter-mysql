package com.example.twittermysql.domain.post.service;

import com.example.twittermysql.domain.post.entity.Timeline;
import com.example.twittermysql.domain.post.repository.TimelineRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {

    final private TimelineRepository timelineRepository;

    public void deliveryToTimeline(Long postId, List<Long> toMemberIds) {
        var timelines = toMemberIds.stream()
                .map((memberId) -> Timeline.builder().memberId(memberId).postId(postId).build())
                .toList();

        timelineRepository.bulkInsert(timelines);
    }
}
