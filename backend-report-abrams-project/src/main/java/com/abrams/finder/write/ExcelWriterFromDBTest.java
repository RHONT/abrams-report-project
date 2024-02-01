package com.abrams.finder.write;

import com.abrams.dto.RowObject;
import com.abrams.dto.RowObjectGroupByTypeWork;
import com.abrams.finder.service.FinderService;
import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.repository.CrudOperationsOperationImpl;
import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ExcelWriterFromDBTest {
    private CrudOperationsAbrams _dbService;
    private final String _fileLocation;
    private Optional<List<RowObjectGroupByTypeWork>> rowsGroup;
    private Optional<List<RowObject>> rowsEach;
    private String _clientName;


    public ExcelWriterFromDBTest(String rootFolder, String clientName) {
        _clientName=clientName;
        _dbService = new CrudOperationsOperationImpl();
        _fileLocation = rootFolder + "/" + clientName + ".xlsx";
    }

    public void writeGroupExcel() throws IOException {
        rowsGroup = _dbService.selectGroupByTypeWork();
        try (OutputStream os = Files.newOutputStream(Paths.get(_fileLocation));
             Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {

            Worksheet sheet = workbook.newWorksheet("Sheet 1");
            createHeadSheet(sheet);

            fillTableGroupValues(rowsGroup, sheet);
        }
    }

    public void writeEachExcel() throws IOException {
        rowsEach = _dbService.selectAll();
        try (OutputStream os = Files.newOutputStream(Paths.get(_fileLocation));
             Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {

            Worksheet sheet = workbook.newWorksheet("Sheet 1");
            createHeadSheet(sheet);

            fillTableEachValues(rowsEach, sheet);
        }
    }

    private void createHeadSheet(Worksheet sheet) {
        sheet.range(0, 0, 0, 1).style().fontName("Arial").
                fontSize(16).bold().fillColor(Color.LIGHT_GREEN).merge().set();
        sheet.value(0, 0, _clientName);  // динам перем.
        sheet.value(1, 0, "Число");
        sheet.value(1, 1, "Тип заказа");
        sheet.value(1, 2, "Имя файла");
        sheet.value(1, 3, "Квадратура");
    }

    private void fillTableGroupValues(Optional<List<RowObjectGroupByTypeWork>> rows, Worksheet ws) {
        int incrRows = 3;
        if (rows.isPresent()) {
            List<RowObjectGroupByTypeWork> rowsWork = rows.get();
            for (var element : rowsWork) {
                ws.value(incrRows, 0, element.get_digitOfMonth());
                ws.value(incrRows, 1, element.get_typeWork());
                ws.value(incrRows, 3, element.getSquereMeters());
                incrRows++;
            }
        }
    }

    private void fillTableEachValues(Optional<List<RowObject>> rows, Worksheet ws) {
        int incrRows = 3;
        if (rows.isPresent()) {
            List<RowObject> rowsWork = rows.get();
            for (var element : rowsWork) {
                ws.value(incrRows, 0, element.get_digitOfMonth());
                ws.value(incrRows, 1, element.get_typeWork());
                ws.value(incrRows, 2, element.get_nameFile());
                ws.value(incrRows, 3, element.get_squareMeters());
                incrRows++;
            }
        }
    }

}
