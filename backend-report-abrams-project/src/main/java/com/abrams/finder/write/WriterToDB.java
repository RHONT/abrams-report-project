package com.abrams.finder.write;

import com.abrams.finder.entity.CustomerInfoOrder;
import com.abrams.finder.search.FinderTargetFiles;
import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.service.CrudOperationsOperationImpl;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class WriterToDB {
    FinderTargetFiles _finderTargetFiles;
    private CrudOperationsAbrams _dbService;

    public WriterToDB(FinderTargetFiles finderTargetFiles) throws IOException {
        _finderTargetFiles = finderTargetFiles;
        _dbService = new CrudOperationsOperationImpl();
        _dbService.createTable();
        writeToDB();
    }

    //todo получить ответ от бд об успешности операции
    private boolean writeToDB() throws IOException {
        List<CustomerInfoOrder> resultInfoFilesList = _finderTargetFiles.getResultFilesList();
        try {
            for (var element : resultInfoFilesList) {
                _dbService.insertValue(element.getDigitOfMonth(), element.getTypeWork(), element.getName());
            }
        } catch (Exception e) {
            throw new NoSuchElementException("Having problems saving the list to the database");
        }
        return true;
    }
}

