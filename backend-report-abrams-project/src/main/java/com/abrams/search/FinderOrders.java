package com.abrams.search;

import com.abrams.etntity.Order;
import com.abrams.parsers.PathToOrder;
import com.abrams.rules.Rules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FinderOrders {

    private final FinderDirectoriesByName _finderDirectoriesByName;
    private final Predicate<Path> isArchive = Rules::isArchive;
    private final Predicate<Path> isReservedFile = Rules::isReservedFile;

    public FinderOrders(FinderDirectoriesByName finderDirectoriesByName) {
        _finderDirectoriesByName = finderDirectoriesByName;
    }

    public List<Order> getResultFilesList() throws IOException {
        List<Path> _listPathForSearch = _finderDirectoriesByName.getResultPathList();
        List<Order> collectedOrders = new ArrayList<>();
        for (Path _path : _listPathForSearch) {
            List<Order> collected = Files.walk(_path)
                    .filter(Files::isRegularFile)
                    .filter(isArchive.negate().and(isReservedFile.negate()))
                    .map(PathToOrder::new)
                    .map(PathToOrder::getSingleCustomersOrder)
                    .collect(Collectors.toList());
            collectedOrders.addAll(collected);
        }
        return collectedOrders;
    }
}
