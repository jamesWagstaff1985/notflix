package com.notflix.streaming.pojos;

import com.notflix.streaming.utils.Encoding;
import com.notflix.streaming.utils.FileUtils;
import com.notflix.streaming.utils.Images;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class TvMetadata {

    @Id
    private String title;
    private String description;
    private SortedSet<VideoMetadata> videoMetadata;
    private byte[] thumbnail;
    private List<TvMetadata> children = new ArrayList<>();
    public TvMetadata(File directory) {
        title = directory.getName();
        setDescription(FileUtils.getFileForMime(directory, MediaType.TEXT_PLAIN));
        setVideoMetadata(FileUtils.getFilesForMime(directory, MimeType.valueOf("video/*")));
        setThumbnail(FileUtils.getFileForMime(directory, MimeType.valueOf("image/*")));
        this.children = Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(File::isDirectory).map(TvMetadata::new).toList();
    }

    public TvMetadata(String base64Url) {
        this(new File(Encoding.decode(base64Url)));
    }

    private void setThumbnail(File file) {
        try {
            byte[] bytes = Files.readAllBytes(Path.of(file.getPath()));
            setThumbnail(bytes);
        } catch (Exception ignored) { }
    }

    public void setThumbnail(byte[] bytes) {
        this.thumbnail = Images.resizeImage(bytes, this.getTitle());
    }

    private void setVideoMetadata(List<File> videos) {
        try {
            this.videoMetadata = videos.stream().map(VideoMetadata::new).collect(Collectors.toCollection(TreeSet::new));
        } catch (Exception ignored) { }
    }

    private void setDescription(File descriptionFile) {
        try {
            this.description = Files.readString(Path.of(descriptionFile.getPath()), Charset.defaultCharset());
        } catch (Exception ignored) { }
    }

}
