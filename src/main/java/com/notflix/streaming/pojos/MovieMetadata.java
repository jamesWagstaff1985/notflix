package com.notflix.streaming.pojos;

import com.notflix.streaming.utils.FileUtils;
import com.notflix.streaming.utils.Images;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieMetadata {

    @Id
    private String title;
    private String description;
    private VideoMetadata videoMetadata;
    private byte[] thumbnail;

    public MovieMetadata(File directory) {
        title = directory.getName();
        setDescription(FileUtils.getFileForMime(directory, MediaType.TEXT_PLAIN));
        setMediaUrl(FileUtils.getFileForMime(directory, MimeType.valueOf("video/*")));
        setThumbnail(FileUtils.getFileForMime(directory, MimeType.valueOf("image/*")));
    }

    private void setThumbnail(File file) {
        try {
            byte[] bytes = Files.readAllBytes(Path.of(file.getPath()));
            setThumbnail(bytes);
        } catch (Exception ignored) { }
    }

    public void setThumbnail(byte[] image) {
        this.thumbnail = Images.resizeImage(image, this.getTitle());
    }

    private void setMediaUrl(File videoFile) {
        this.videoMetadata = new VideoMetadata(videoFile);
    }

    private void setDescription(File descriptionFile) {
        try {
            this.description = Files.readString(Path.of(descriptionFile.getPath()), Charset.defaultCharset());
        } catch (Exception ignored) { }
    }

}
