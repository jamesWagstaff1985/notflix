package com.notflix.streaming.utils;

import org.springframework.util.MimeType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileUtils {

    public static File readDirectory(String path) {
        File file = readFile(path);
        assert file.isDirectory() : "Directory either is a file or doesn't exist > " + path;
        return file;
    }

    public static File readFile(String path) {
        File file = new File(path);
        assert file.isFile() : "File either is a directory or doesn't exist > " + path;
        return file;
    }

    public static MimeType getMime(File file) throws IOException {
        Path path = file.toPath();
        String mimeType = Files.probeContentType(path);
        return mimeType == null ? null : MimeType.valueOf(mimeType);
    }

    public static File getFileForMime(File directory, MimeType mime) {
        assert directory.isDirectory() : "Given file must be a directory > " + directory.getPath();
        return Arrays.stream(directory.listFiles()).filter(file -> !file.isDirectory()).filter(file -> isOfMimeType(file, mime)).findFirst().orElse(null);
    }

    public static List<File> getFilesForMime(File directory, MimeType mimeType) {
        assert directory.isDirectory() : "Given file must be a directory > " + directory.getPath();
        return Arrays.stream(directory.listFiles()).filter(file -> !file.isDirectory()).filter(file -> isOfMimeType(file, mimeType)).toList();
    }

    private static final List<String> BLACK_LIST = List.of(".x264", ".AAC", ".mp4", ".720p", ".WEB-DL");
    public static String cleanName(String name) {
        String[] nameArr = { name };
        BLACK_LIST.forEach(item -> nameArr[0] = nameArr[0].replaceAll(item, ""));
        return nameArr[0].replaceAll("\\.", " ");
    }

    private static boolean isOfMimeType(File file, MimeType mime) {
        try {
            if(mime.toString().contains("*")) return compareWildCardMime(getMime(file), mime);
            return Objects.equals(getMime(file), mime);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean compareWildCardMime(MimeType fileMime, MimeType searchMime) {
        return fileMime != null && fileMime.toString().contains(searchMime.toString().substring(0, searchMime.toString().length() - 2));
    }
}
