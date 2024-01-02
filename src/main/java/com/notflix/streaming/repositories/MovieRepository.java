package com.notflix.streaming.repositories;

import com.notflix.streaming.pojos.MovieMetadata;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<MovieMetadata, String>, ListCrudRepository<MovieMetadata, String> {
    MovieMetadata findByVideoMetadataEncodedUri(String uri);
}
