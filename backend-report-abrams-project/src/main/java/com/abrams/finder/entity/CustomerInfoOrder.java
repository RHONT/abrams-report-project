package com.abrams.finder.entity;

import java.nio.file.Path;

/**
 * Entity to save to database "report"
 */
public class CustomerInfoOrder {
    private String _digitOfMonth;
    private String _typeWork;
    private String _fileName;

    public CustomerInfoOrder(Path path) {
        _fileName = path.getFileName().toString();
        _typeWork = getDirName(path);
        _digitOfMonth = getDirNameUpTwoLevel(path);
    }

    private String getDirName(Path path) {
        int temp = path.getNameCount();
        return path.getName(temp - 2).toString();
    }

    private String getDirNameUpTwoLevel(Path path) {
        int temp = path.getNameCount();
        return path.getName(temp - 4).toString();
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
