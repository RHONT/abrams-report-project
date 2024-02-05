package com.abrams.outputto;

import com.abrams.dto.SingleCustomersOrder;
import com.abrams.search.FinderFiles;
import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.service.CrudOperationsOperationImpl;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class WriterToDB {
    FinderFiles _finderFiles;
    private final CrudOperationsAbrams _dbService;

    public WriterToDB(FinderFiles finderFiles) throws IOException {
        _finderFiles = finderFiles;
        _dbService = new CrudOperationsOperationImpl();
        _dbService.createTable();
        writeToDB();
    }

    private void writeToDB() throws IOException {
        List<SingleCustomersOrder> resultInfoFilesList = _finderFiles.getResultFilesList();
        try {
            for (var customer : resultInfoFilesList) {
                _dbService.insertValue(customer);
            }
        } catch (Exception e) {
            throw new NoSuchElementException("Having problems saving the list to the database");
        }
    }
}

