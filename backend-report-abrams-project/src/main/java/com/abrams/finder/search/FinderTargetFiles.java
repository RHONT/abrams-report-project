package com.abrams.finder.search;

import com.abrams.finder.dto.InfoAboutFileByNameAndParentNameDirectory;
import com.abrams.finder.service.FinderService;
import com.abrams.finder.utils.UtilClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FinderTargetFiles {

    private FinderTargetDirectories _finderTargetDirectories;
    private final Predicate<Path> isArchive = UtilClass::isArchive;
    private List<Path> _listPathForSearch;

    public FinderTargetFiles(FinderTargetDirectories finderTargetDirectories) throws IOException {
        _finderTargetDirectories=finderTargetDirectories;
    }

    public List<InfoAboutFileByNameAndParentNameDirectory> getResultFilesList() throws IOException {
        _listPathForSearch=_finderTargetDirectories.getResultPathList();
        List<InfoAboutFileByNameAndParentNameDirectory> collected = null;
        for (int i = 0; i < _listPathForSearch.size(); i++) {
            Path _path = _listPathForSearch.get(i);
             collected = Files.walk(_path)
                    .filter(Files::isRegularFile)
                    .filter(isArchive.negate())
                    .map(InfoAboutFileByNameAndParentNameDirectory::new)
                    .collect(Collectors.toList());
        }
        return collected;
    }
}
