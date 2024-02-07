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
    private final String _nameDir;
    private final String _rootFolder;
    private final Predicate<Path> isArchive = Rules::isArchive;
    private final Predicate<Path> isReservedFile = Rules::isReservedFile;

    public FinderOrders(String nameDir, String rootFolder) {
        _nameDir=nameDir;
        _rootFolder=rootFolder;
    }

    public List<Order> giveOrders() throws IOException {
        List<Path> _listPathForSearch = givePaths();
        List<Order> collectedOrders = new ArrayList<>();
        for (Path _path : _listPathForSearch) {
            List<Order> collected = Files.walk(_path)
                    .filter(Files::isRegularFile)
                    .filter(isArchive.negate().and(isReservedFile.negate()))
                    .map(PathToOrder::new)
                    .map(PathToOrder::getOrder)
                    .collect(Collectors.toList());
            collectedOrders.addAll(collected);
        }
        return collectedOrders;
    }

    private List<Path> givePaths() throws IOException {
        return Files.walk(Path.of(_rootFolder))
                .filter(Files::isDirectory)
                .filter(path -> path.getFileName().toString().equals(_nameDir))
                .collect(Collectors.toList());
    }
}
