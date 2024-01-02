package com.notflix.streaming.services;

import com.notflix.streaming.pojos.TvMetadata;
import com.notflix.streaming.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class TvService {

    @Value("#{systemProperties['os.name'].contains('Windows') ? '${media.drive}:${media.source-root}' : '${media.source-root}'}")
    private String root;

    @Value("${media.tvshows}")
    private String tvPath;

    public Stream<TvMetadata> getAllMetaData() {
        return Arrays.stream(FileUtils.readDirectory(root + tvPath).listFiles()).map(TvMetadata::new);
    }

}
