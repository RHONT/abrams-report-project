package com.abrams.finder.service;

import com.abrams.finder.utils.UtilClass;
import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.repository.CrudOperationsOperationImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FinderService {
    private CrudOperationsAbrams _dbService;
    private String _rootFolder;
    private String _nameClientDir;
    private Predicate<Path> isArchive = UtilClass::isArchive;
    private List<Path> _targetDirToSearch;
    private TreeMap<String, Map<String, List<String>>> _dataResultMap;

    public FinderService(String nameClientDir, String rootFolder) throws IOException {
        _dbService =new CrudOperationsOperationImpl();
        _dbService.createTable();
        _nameClientDir = nameClientDir;
        _rootFolder = rootFolder;
        _targetDirToSearch = getTargetDirectories(Paths.get(rootFolder));
        insertInDB();
        _dataResultMap = createMapData();
    }

    public List<Path> getTargetDirectories(Path targetPath) throws IOException {
        return Files.walk(targetPath)
                .filter(Files::isDirectory)
                .filter(path -> path.getFileName().toString().equals(_nameClientDir))
                .collect(Collectors.toList());
    }

    public TreeMap<String, Map<String, List<String>>> createMapData() throws IOException {
        TreeMap<String, Map<String, List<String>>> _dictionary = new TreeMap<>();
        for (int i = 0; i < _targetDirToSearch.size(); i++) {
            Path _path = _targetDirToSearch.get(i);
            Map<String, List<String>> collected = Files.walk(_path)
                    .filter(Files::isRegularFile)
                    .filter(isArchive.negate())
                    .map(SelectedFile::new)
                    .collect(Collectors.
                            groupingBy(SelectedFile::getTypeWork,
                                    Collectors.
                                            mapping(SelectedFile::getName, Collectors.toList())));
            _dictionary.put(getParentNameDir(_path), collected);
        }
        return _dictionary;
    }

    public void insertInDB() throws IOException {
        for (int i = 0; i < _targetDirToSearch.size(); i++) {
            Path _path = _targetDirToSearch.get(i);
            List<SelectedFile> collected = Files.walk(_path)
                    .filter(Files::isRegularFile)
                    .filter(isArchive.negate())
                    .map(SelectedFile::new)
                    .collect(Collectors.toList());

            for(SelectedFile element:collected){
                _dbService.insertValue(getParentNameDir(_path), element.getTypeWork(), element.getName());
            }
        }

    }

    private String getParentNameDir(Path path) {
        int temp = path.getNameCount();
        return path.getName(temp - 2).toString();
    }

    public void PrintToConsole() throws IOException {
        for (Map.Entry<String, Map<String, List<String>>> element : createMapData().entrySet()) {
            System.out.println("Число - " + element.getKey());
            for (Map.Entry<String, List<String>> inenr : element.getValue().entrySet()) {
                System.out.println("--Тип продукции: " + inenr.getKey());
                System.out.println("--------Внутренние файлы\n" + inenr.getValue());
            }
        }
    }


    private class SelectedFile {
        String _fileName;
        String _typeWork;
        public SelectedFile(Path path) {
            if (Files.isRegularFile(path)) {
                _fileName = path.getFileName().toString();
            }
            _typeWork = getParentNameDir(path);
        }
        public String getTypeWork() {
            return _typeWork;
        }
        public String getName() {
            return _fileName;
        }
    }

    public Map<String, Map<String, List<String>>> getDataResultMap() {
        return _dataResultMap;
    }

    public String get_rootFolder() {
        return _rootFolder;
    }

    public String get_nameClientDir() {
        return _nameClientDir;
    }
}
