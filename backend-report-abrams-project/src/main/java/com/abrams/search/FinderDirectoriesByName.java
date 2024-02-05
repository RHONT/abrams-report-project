package com.abrams.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FinderDirectoriesByName extends Finder {
    private final String _nameClientDir;

    public FinderDirectoriesByName(String nameClientDir, String rootFolder){
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
