package com.abrams.finder.creatorsreports;

import com.abrams.dto.SingleCustomersOrder;
import com.abrams.dto.GroupedCustomerOrder;
import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.service.CrudOperationsOperationImpl;
import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ExcelCreatorFromDB {
    private final CrudOperationsAbrams _dbService;
    private final String _fileLocation;
    private final String _customerName;


    public ExcelCreatorFromDB(String customerName, String folderSave) {
        _customerName = customerName;
        _dbService = new CrudOperationsOperationImpl();
        _fileLocation = folderSave + "/" + customerName + ".xlsx";
    }

    public void createGroupReportByDigitMontAndTypeWork() throws IOException {
        Optional<List<GroupedCustomerOrder>> rowsGroup = _dbService.selectGroupByTypeWork();
        try (OutputStream os = Files.newOutputStream(Paths.get(_fileLocation));
             Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {

            Worksheet sheet = workbook.newWorksheet("Sheet 1");
            createHeadSheet(sheet);
            rowsGroup.ifPresent(groupedCustomerOrders -> fillTableGroupValues(groupedCustomerOrders, sheet));
        }
    }

    public void createDetailedReport() throws IOException {
        Optional<List<SingleCustomersOrder>> rowsEach = _dbService.selectAll();
        try (OutputStream os = Files.newOutputStream(Paths.get(_fileLocation));
             Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {

            Worksheet sheet = workbook.newWorksheet("Sheet 1");
            createHeadSheet(sheet);

            rowsEach.ifPresent(singleCustomersOrders -> fillTableEachValues(singleCustomersOrders, sheet));
        }
    }

    private void createHeadSheet(Worksheet sheet) {
        sheet.range(0, 0, 0, 1).style().fontName("Arial").
                fontSize(16).bold().fillColor(Color.LIGHT_GREEN).merge().set();
        sheet.value(0, 0, _customerName);  // динам перем.
        sheet.value(1, 0, "Число");
        sheet.value(1, 1, "Тип заказа");
        sheet.value(1, 2, "Имя файла");
        sheet.value(1, 3, "Квадратура");
    }

    private void fillTableGroupValues(List<GroupedCustomerOrder> rows, Worksheet ws) {
        int incrRows = 3;
        for (var element : rows) {
            ws.value(incrRows, 0, element.getDigitOfMonth());
            ws.value(incrRows, 1, element.getTypeWork());
            ws.value(incrRows, 3, element.getSquareMeters());
            incrRows++;
        }
    }

    private void fillTableEachValues(List<SingleCustomersOrder> rows, Worksheet ws) {
        int incrRows = 3;
        for (var element : rows) {
            ws.value(incrRows, 0, element.get_digitOfMonth());
            ws.value(incrRows, 1, element.get_typeWork());
            ws.value(incrRows, 2, element.get_nameFile());
            ws.value(incrRows, 3, element.get_squareMeters());
            incrRows++;
        }
    }

}
