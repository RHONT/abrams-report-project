package com.abrams.search;

import com.abrams.dto.SingleCustomersOrder;
import com.abrams.mapper.PathToSingleCustomerOrder;
import com.abrams.rules.Rules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FinderFiles {

    private final FinderDirectoriesByName _finderDirectoriesByName;
    private final Predicate<Path> isArchive = Rules::isArchive;
    private final Predicate<Path> isReservedFile = Rules::isReservedFile;

    public FinderFiles(FinderDirectoriesByName finderDirectoriesByName) {
        _finderDirectoriesByName = finderDirectoriesByName;
    }

    public List<SingleCustomersOrder> getResultFilesList() throws IOException {
        List<Path> _listPathForSearch = _finderDirectoriesByName.getResultPathList();
        List<SingleCustomersOrder> collectedResult = new ArrayList<>();
        for (Path _path : _listPathForSearch) {
            List<SingleCustomersOrder> collected = Files.walk(_path)
                    .filter(Files::isRegularFile)
                    .filter(isArchive.negate().and(isReservedFile.negate()))
                    .map(PathToSingleCustomerOrder::new)
                    .map(PathToSingleCustomerOrder::getSingleCustomersOrder)
                    .collect(Collectors.toList());
            collectedResult.addAll(collected);
        }
        return collectedResult;
    }
}
