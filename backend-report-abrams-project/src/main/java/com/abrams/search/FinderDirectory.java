package com.abrams.search;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public abstract class FinderDirectory {
    protected String _rootPath;
    abstract Collection<Path> givePaths() throws IOException;
}
