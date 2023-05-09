package com.example.twittermysql.domain.post.service;

import com.example.twittermysql.domain.post.entity.Timeline;
import com.example.twittermysql.domain.post.repository.TimelineRepository;
import com.example.twittermysql.util.CursorRequest;
import com.example.twittermysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TimelineReadService {

    final private TimelineRepository timelineRepository;

    public PageCursor<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        var timelines = findAllBy(memberId, cursorRequest);
        var nextKey = timelines.stream().mapToLong(Timeline::getId).min()
                .orElse(CursorRequest.NONE_KEY);

        return new PageCursor<>(cursorRequest.next(nextKey), timelines);
    }

    private List<Timeline> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return timelineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(
                    cursorRequest.key(), memberId, cursorRequest.size());
        }

        return timelineRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
    }
}
