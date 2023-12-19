package com.yuki.Domain.Const;


import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class CloudFileType {
    private final String[] _imgType = {
            ".bmp",".jpg",".png",".tif",".gif",
            ".svg",".psd",".webp",".jpeg"};

    private final String[] _videoType = {".mp4", ".avi", ".mkv", ".mov", ".wmv",
            ".flv", ".webm", ".3gp", ".mpeg", ".rm",
            ".vob", ".ogg"};

    private final String[] _soundType = {".mp3", ".wav", ".flac", ".aac", ".ogg",
            ".wma", ".m4a", ".opus", ".mid", ".amr"};

    private final String[] _docType = {".pdf", ".doc", ".docx", ".ppt", ".pptx",
            ".xls", ".xlsx", ".odt", ".odp", ".ods", ".txt", ".epub"};


    public final List<String> imgType = Arrays.asList(_imgType);
    public final List<String> videoType = Arrays.asList(_videoType);
    public final List<String> soundType = Arrays.asList(_soundType);
    public final List<String> docType = Arrays.asList(_docType);


}
