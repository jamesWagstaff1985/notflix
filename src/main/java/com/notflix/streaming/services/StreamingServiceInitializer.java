package com.notflix.streaming.services;

import com.notflix.streaming.repositories.MovieRepository;
import com.notflix.streaming.repositories.TvRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamingServiceInitializer {

    private final MovieService movieService;
    private final MovieRepository movieRepository;

    private final TvService tvService;
    private final TvRepository tvRepository;

    public void init() {
        System.out.println("*******************************");
        movieService.getAllMetaData()
                .filter(metadata -> !movieRepository.existsById(metadata.getTitle()))
                .forEach(movie -> {
                    System.out.println("*******************************");
                    System.out.println(movie.getTitle());
                    movieRepository.save(movie);
                });
        tvService.getAllMetaData()
                .filter(metadata -> !tvRepository.existsById(metadata.getTitle()))
                .forEach(tvRepository::save);
    }

    // Use this as reference for file watcher - https://dzone.com/articles/listening-to-fileevents-with-java-nio

}
