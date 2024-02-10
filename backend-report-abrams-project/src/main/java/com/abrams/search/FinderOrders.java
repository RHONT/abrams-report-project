package com.abrams.search;

import com.abrams.etntity.Order;
import com.abrams.parsers.PathToOrder;
import com.abrams.rules.Rules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FinderOrders {
    private static final Logger logger = LogManager.getLogger(FinderOrders.class);
    private final String _nameDir;
    private final String _rootFolder;
    private final Predicate<Path> isArchive = Rules::isArchive;
    private final Predicate<Path> isReservedFile = Rules::isReservedFile;

    public FinderOrders(String nameDir, String rootFolder) {
        _nameDir = nameDir;
        _rootFolder = rootFolder;
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
        logger.info("Целевые папки: {}", _listPathForSearch.size());
        logger.info("Целевые файлы: {}", collectedOrders.size());
        if (collectedOrders.isEmpty()) {
            logger.warn("Папки либо пусты, либо в них нет удовлетворяющих результатов");
            throw new NoSuchElementException("Files not found");
        }
        return collectedOrders;
    }

    private List<Path> givePaths() throws IOException {
        Path _path = Path.of(_rootFolder);
        checkPath(_path);
        return Files.walk(_path)
                .filter(Files::isDirectory)
                .filter(path -> path.getFileName().toString().equals(_nameDir))
                .collect(Collectors.toList());
    }

    private void checkPath(Path _path) throws NotDirectoryException {
        if (Files.notExists(_path)) {
            logger.warn("Ошибочный ввод папки: " + _path.getFileName());
            throw new NotDirectoryException("directory not exist");
        }
    }
}
