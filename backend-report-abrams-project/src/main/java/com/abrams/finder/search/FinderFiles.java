package com.abrams.finder.search;

import com.abrams.finder.dto.CustomerInfoOrder;
import com.abrams.finder.rules.Rules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FinderFiles {

    private FinderDirectoriesByName _finderDirectoriesByName;
    private final Predicate<Path> isArchive = Rules::isArchive;
    private final Predicate<Path> isReservedFile = Rules::isReservedFile;
    private List<Path> _listPathForSearch;

    public FinderFiles(FinderDirectoriesByName finderDirectoriesByName) {
        _finderDirectoriesByName = finderDirectoriesByName;
    }

    public List<CustomerInfoOrder> getResultFilesList() throws IOException {
        _listPathForSearch = _finderDirectoriesByName.getResultPathList();
        List<CustomerInfoOrder> collectedResult = new ArrayList<>();
            for (int i = 0; i < _listPathForSearch.size(); i++) {
                Path _path = _listPathForSearch.get(i);
                List<CustomerInfoOrder> collected = Files.walk(_path)
                        .filter(Files::isRegularFile)
                        .filter(isArchive.negate().and(isReservedFile.negate()))
                        .map(CustomerInfoOrder::new)
                        .collect(Collectors.toList());
                collectedResult.addAll(collected);
            }

        return collectedResult;
    }
}
