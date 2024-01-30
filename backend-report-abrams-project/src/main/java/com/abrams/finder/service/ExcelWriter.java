package com.abrams.finder.service;

import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.abrams.finder.utils.UtilClass.*;

public class ExcelWriter {
    FinderService _finderService;
    private final String _fileLocation;
    private TreeMap<String, Map<String, List<String>>> _dataResultMap;

    public ExcelWriter(FinderService finderService) {
        _finderService = finderService;
        _dataResultMap = (TreeMap<String, Map<String, List<String>>>) _finderService.getDataResultMap();
        _fileLocation = _finderService.get_rootFolder() +"/"+ _finderService.get_nameClientDir() + ".xlsx";
    }

    public void writeExcel() throws IOException {

        try (OutputStream os = Files.newOutputStream(Paths.get(_fileLocation));
             Workbook workbook = new Workbook(os, "MyApplication", "1.0")) {

            Worksheet sheet = workbook.newWorksheet("Sheet 1");
            createHeadSheet(sheet);

            fillTableValues(_dataResultMap, sheet);
        }
    }

    private void createHeadSheet(Worksheet sheet) {
        sheet.range(0, 0, 0, 1).style().fontName("Arial").
                fontSize(16).bold().fillColor(Color.LIGHT_GREEN).merge().set();
        sheet.value(0, 0, _finderService.get_nameClientDir());  // динам перем.
        sheet.value(1, 0, "Число");
        sheet.value(1, 1, "Тип заказа");
        sheet.value(1, 2, "Имя файла");
        sheet.value(1, 3, "Квадратура");
    }

    private void fillTableValues(Map<String, Map<String, List<String>>> _dataResultMap, Worksheet ws) {
        int incrRows = 3;
        for (Map.Entry<String, Map<String, List<String>>> dayOfTheMonth : _dataResultMap.entrySet()) {
            String _dayOfTheMonth = dayOfTheMonth.getKey();
            for (Map.Entry<String, List<String>> typeWork : dayOfTheMonth.getValue().entrySet()) {
                String _typeWork = typeWork.getKey();
                for (String name : typeWork.getValue()) {
                    ws.value(incrRows, 0, _dayOfTheMonth);
                    ws.value(incrRows, 1, _typeWork);
                    ws.value(incrRows, 2, name);
                    ws.value(incrRows, 3, getSquareMeters(name));
                    incrRows++;
                }
            }
        }
    }

}
