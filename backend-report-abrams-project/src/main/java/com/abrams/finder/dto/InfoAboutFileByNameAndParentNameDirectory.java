package com.abrams.finder.dto;

import java.nio.file.Files;
import java.nio.file.Path;

public class InfoAboutFileByNameAndParentNameDirectory {
    private String _fileName;
    private String _typeWork;
    private String _digitOfMonth;

    public InfoAboutFileByNameAndParentNameDirectory(Path path) {
        if (Files.isRegularFile(path)) {
            _fileName = path.getFileName().toString();
        }
        _typeWork = getDirName(path);
        _digitOfMonth=getDirNameUpOneLevel(path);
    }

    private String getDirName(Path path) {
        int temp = path.getNameCount();
        return path.getName(temp - 2).toString();
    }

    private String getDirNameUpOneLevel(Path path) {
        int temp = path.getNameCount();
        return path.getName(temp - 3).toString();
    }

    public String getName() {
        return _fileName;
    }

    public String getTypeWork() {
        return _typeWork;
    }

    public String getDigitOfMonth() {
        return _digitOfMonth;
    }
}
