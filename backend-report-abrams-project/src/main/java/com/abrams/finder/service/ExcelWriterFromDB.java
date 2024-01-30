package com.abrams.finder.service;

import com.abrams.dto.RowObjectGroupByTypeWork;
import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.repository.CrudOperationsOperationImpl;
import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ExcelWriterFromDB {
    FinderService _finderService;
    CrudOperationsAbrams _dbService;
    private final String _fileLocation;
    Optional<List<RowObjectGroupByTypeWork>> rows;



    public ExcelWriterFromDB(FinderService finderService) {
        _dbService=new CrudOperationsOperationImpl();
        rows = _dbService.selectGroupByTypeWork();
        _finderService = finderService;
        _fileLocation = _finderService.get_rootFolder() +"/"+ _finderService.get_nameClientDir() + ".xlsx";
    }

    public void writeExcel() throws IOException {

        try (OutputStream os = Files.newOutputStream(Paths.get(_fileLocation));
             Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {

            Worksheet sheet = workbook.newWorksheet("Sheet 1");
            createHeadSheet(sheet);

            fillTableGroupValues(rows, sheet);
        }
    }

    private void createHeadSheet(Worksheet sheet) {
        sheet.range(0, 0, 0, 1).style().fontName("Arial").
                fontSize(16).bold().fillColor(Color.LIGHT_GREEN).merge().set();
        sheet.value(0, 0, _finderService.get_nameClientDir());  // динам перем.
        sheet.value(1, 0, "Число");
        sheet.value(1, 1, "Тип заказа");
        sheet.value(1, 2, "Квадратура");
    }

    private void fillTableGroupValues(Optional<List<RowObjectGroupByTypeWork>> rows, Worksheet ws) {
        int incrRows = 3;
        if (rows.isPresent()) {
            List<RowObjectGroupByTypeWork> rowsWork = rows.get();
            for (var element : rowsWork) {
                ws.value(incrRows, 0, element.get_digitOfMonth());
                ws.value(incrRows, 1, element.get_typeWork());
                ws.value(incrRows, 2, element.getSquereMeters());
                incrRows++;
            }
        }
    }

}
