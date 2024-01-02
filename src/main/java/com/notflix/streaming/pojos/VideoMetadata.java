package com.notflix.streaming.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.notflix.streaming.utils.Encoding;
import com.notflix.streaming.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoMetadata implements Comparable<VideoMetadata> {

    private String encodedUri;
    private String title;
    private boolean watched = false;

    public VideoMetadata(File episode) {
        setUri(episode.getPath());
        setTitle(episode);
    }

    public void setTitle(File episode) {
        this.title = FileUtils.cleanName(episode.getName());
        // try to remove series title from episode name
        Pattern pattern = Pattern.compile("s[0-9]+.+e[0-9]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.title);
        if(matcher.find()) {
          String[] split = pattern.split(this.title);
          this.title = String.format("%s %s", matcher.group(), split[1]);
        }
    }

    public void setUri(String uri) {
        this.encodedUri = Encoding.encode(uri);
    }

    @JsonIgnore
    public String getDecodedUri() {
        return Encoding.decode(this.encodedUri);
    }

    @Override
    public int compareTo(VideoMetadata that) {
        return this.title.compareTo(that.title);
    }
}
