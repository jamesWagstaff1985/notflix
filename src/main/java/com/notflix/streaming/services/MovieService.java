package com.notflix.streaming.services;

import com.notflix.streaming.pojos.MovieMetadata;
import com.notflix.streaming.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class MovieService {

    @Value("#{systemProperties['os.name'].contains('Windows') ? '${media.drive}:${media.source-root}' : '${media.source-root}'}")
    private String root;

    @Value("${media.movies}")
    private String moviesPath;

    public Stream<MovieMetadata> getAllMetaData() {
        return Arrays.stream(FileUtils.readDirectory(root + moviesPath).listFiles()).map(MovieMetadata::new);
    }

}
