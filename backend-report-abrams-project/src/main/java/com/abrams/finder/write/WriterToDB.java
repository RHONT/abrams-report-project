package com.abrams.finder.write;

import com.abrams.finder.dto.InfoAboutFileByNameAndParentNameDirectory;
import com.abrams.finder.search.FinderTargetFiles;
import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.repository.CrudOperationsOperationImpl;

import java.io.IOException;
import java.util.List;

public class WriterToDB {
    FinderTargetFiles _finderTargetFiles;
    private CrudOperationsAbrams _dbService;

    public WriterToDB(FinderTargetFiles finderTargetFiles) {
        _finderTargetFiles = finderTargetFiles;
        _dbService = new CrudOperationsOperationImpl();
    }

    //todo получить ответ от бд об успешности операции
    private boolean writeToDB() throws IOException {
        List<InfoAboutFileByNameAndParentNameDirectory> resultInfoFilesList = _finderTargetFiles.getResultFilesList();
        for (var element : resultInfoFilesList) {
            _dbService.insertValue(element.getDigitOfMonth(), element.getTypeWork(), element.getName());
        }
        return true;
    }
}

