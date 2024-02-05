package com.abrams.reports;

import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.service.CrudOperationsOperationImpl;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public abstract class Report<T> {
    protected final CrudOperationsAbrams _dbService;
    protected final String _fileName;
    protected final String _fullFolderToSave;
    protected int _currRowsIncrement=0;


    public Report(String DirectoryForSave, String fileName) {
        _dbService = new CrudOperationsOperationImpl();
        _fileName = fileName;
        _fullFolderToSave = DirectoryForSave + "/" + fileName + ".xlsx";
    }

    protected abstract Collection<T> getListDto();

    protected abstract void fillTable(Collection<T> listDto, Worksheet sheet);

    protected abstract void createHeadSheet(Worksheet sheet);

    public boolean save() throws IOException {
        try (OutputStream os = Files.newOutputStream(Paths.get(_fullFolderToSave));
             Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {
            Worksheet sheet = workbook.newWorksheet("Sheet 1");
            createHeadSheet(sheet);
            fillTable(getListDto(), sheet);
        }
        return true;
    }

}
