package com.abrams.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FinderDirectoriesByName extends FinderDirectory {
    private final String _name;

    public FinderDirectoriesByName(String nameDir, String rootFolder){
        _name = nameDir;
        _rootPath = rootFolder;
    }

    @Override
    List<Path> givePaths() throws IOException {
            return Files.walk(Path.of(_rootPath))
                    .filter(Files::isDirectory)
                    .filter(path -> path.getFileName().toString().equals(_name))
                    .collect(Collectors.toList());
    }
}
