package com.notflix.streaming.repositories;

import com.notflix.streaming.pojos.TvMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvRepository extends MongoRepository<TvMetadata, String> {

    TvMetadata findByChildrenVideoMetadataEncodedUri(String uri);

}
