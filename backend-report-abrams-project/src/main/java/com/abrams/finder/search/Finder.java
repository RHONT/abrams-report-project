package com.abrams.finder.search;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public abstract class Finder {
    protected String _rootPath;
    abstract List<Path> getResultPathList() throws IOException;
}
