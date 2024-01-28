package com.abrams.finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Finder {
    private String _rootFolder;
    private String _targetDir;
    Path _path;
    Predicate<Path> isArchive= UtilClass::isArchive;

    public Finder(String targetDir,String pathForSearch) {
        _targetDir=targetDir;
        _rootFolder=pathForSearch;
        _path = Paths.get(pathForSearch);
    }

    public List<Path> getTargetDirectory() throws IOException {
       return Files.walk(_path)
                .filter(Files::isDirectory)
                .filter(path-> path.getFileName().toString().equals(_targetDir))
               .collect(Collectors.toList());
    }

    public Map<String, Map<String, List<String>>> createMapData() throws IOException {
        List<Path> _pathList=getTargetDirectory();
        Map<String, Map<String, List<String>>>  _dictionary = new HashMap<>();
        for (int i = 0; i < _pathList.size(); i++) {
            Path _path=_pathList.get(i);
            Map<String, List<String>> collected = Files.walk(_path)
                    .filter(Files::isRegularFile)
                    .filter(isArchive.negate())
                    .map(TargetObject::new)
                    .collect(Collectors.
                            groupingBy(TargetObject::get_type,
                                    Collectors.
                                            mapping(TargetObject::get_name, Collectors.toList())));
            _dictionary.put(getParentNameDir(_path),collected);
        }
        return _dictionary;
    }

    private String getParentNameDir(Path path){
        int temp=path.getNameCount();
        return path.getName(temp-2).toString();
    }

    public void PrintToConsole() throws IOException {
        for (Map.Entry<String, Map<String, List<String>>> element : createMapData().entrySet()) {
            System.out.println("Число - " + element.getKey());
            for (Map.Entry<String, List<String>> inenr : element.getValue().entrySet()){
                System.out.println("--Тип продукции: " + inenr.getKey());
                System.out.println("--------Внутренние файлы\n" + inenr.getValue());
            }
        }
    }



    private class TargetObject{
        String _type;
        String _name;

        public TargetObject(Path path) {
            if (Files.isRegularFile(path)) {
                _name=path.getFileName().toString();
            }
            _type=getParentNameDir(path);
        }

        public String get_type() {
            return _type;
        }

        public String get_name() {
            return _name;
        }
    }
}
