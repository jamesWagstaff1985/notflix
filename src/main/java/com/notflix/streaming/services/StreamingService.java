package com.notflix.streaming.services;

import com.notflix.streaming.pojos.MovieMetadata;
import com.notflix.streaming.pojos.TvMetadata;
import com.notflix.streaming.pojos.VideoMetadata;
import com.notflix.streaming.repositories.MovieRepository;
import com.notflix.streaming.repositories.TvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final MovieRepository movieRepository;
    private final TvRepository tvRepository;

    public Mono<Resource> getVideo(VideoMetadata metadata) {
        return Mono.fromSupplier(() -> new FileSystemResource(metadata.getDecodedUri()));
    }

    public Page<MovieMetadata> getMovies(Integer page, Integer size) {
        return movieRepository.findAll(PageRequest.of(page, size, Sort.by("title")));
    }

    public Page<TvMetadata> getTvShowsEpisodes(Integer page, Integer size) {
        return tvRepository.findAll(PageRequest.of(page, size, Sort.by("title")));
    }

    //TODO: this could definitely be improved!!
    public TvMetadata updateByChildrenVideoMetadataEncodedUri(String uri) {
        TvMetadata toUpdate = tvRepository.findByChildrenVideoMetadataEncodedUri(uri);
        toUpdate.getChildren().forEach(child -> child.getVideoMetadata().forEach(metadata -> {
            if(metadata.getEncodedUri().equals(uri)) {
                metadata.setWatched(true);
                tvRepository.save(toUpdate);
            }
        }));
        return toUpdate;
    }

    public MovieMetadata updateByVideoMetadataEncodedUri(String uri) {
        MovieMetadata toUpdate = movieRepository.findByVideoMetadataEncodedUri(uri);
        toUpdate.getVideoMetadata().setWatched(true);
        movieRepository.save(toUpdate);
        return toUpdate;
    }
}
