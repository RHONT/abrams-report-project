package com.abrams.finder.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FinderTargetDirectoriesByName extends Finder {

    private final String _nameClientDir;

    public FinderTargetDirectoriesByName(String nameClientDir, String rootFolder){
        _nameClientDir = nameClientDir;
        _rootPath = rootFolder;
    }

    @Override
    List<Path> getResultPathList() throws IOException {
            return Files.walk(Path.of(_rootPath))
                    .filter(Files::isDirectory)
                    .filter(path -> path.getFileName().toString().equals(_nameClientDir))
                    .collect(Collectors.toList());
    }
}
