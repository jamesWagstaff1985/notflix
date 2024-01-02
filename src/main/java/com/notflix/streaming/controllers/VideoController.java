package com.notflix.streaming.controllers;

import com.notflix.streaming.pojos.MovieMetadata;
import com.notflix.streaming.pojos.TvMetadata;
import com.notflix.streaming.pojos.VideoMetadata;
import com.notflix.streaming.repositories.TvRepository;
import com.notflix.streaming.services.StreamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class VideoController {

    private final StreamingService service;
    @GetMapping(value = "video/{encodedUri}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String encodedUri, @RequestHeader("Range") String range) {
        log.info(range);
        VideoMetadata videoMetadata = VideoMetadata.builder().encodedUri(encodedUri).build();
        return service.getVideo(videoMetadata);
    }

    @GetMapping(value = "movies")
    public Page<MovieMetadata> getMovies(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        return service.getMovies(page, size);
    }

    @GetMapping(value = "tv-shows")
    public Page<TvMetadata> getTvShowsEpisodes(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        return service.getTvShowsEpisodes(page, size);
    }

    @GetMapping("setWatchedTv")
    public TvMetadata setWatchedTv(@RequestParam String uri) {
        return service.updateByChildrenVideoMetadataEncodedUri(uri);
    }

    @GetMapping("setWatchedMovie")
    public MovieMetadata setWatchedMovie(@RequestParam String uri) {
        return service.updateByVideoMetadataEncodedUri(uri);
    }

}
