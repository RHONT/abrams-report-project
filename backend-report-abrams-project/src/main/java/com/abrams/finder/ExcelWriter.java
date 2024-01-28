package com.abrams.finder;

import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.abrams.finder.UtilClass.*;

public class ExcelWriter {
    private Finder finder;

    public static void writeExcel(Finder finder) throws IOException {
        Map<String, Map<String, List<String>>> _dataResultMap = finder.getDataResultMap();
        String path = finder.get_rootFolder();
        String fileLocation = path +"/"+ finder.get_nameClientDir() + ".xlsx";

        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation));
             Workbook wb = new Workbook(os, "MyApplication", "1.0")) {

            Worksheet ws = wb.newWorksheet("Sheet 1");
//            ws.width(0, 25);
//            ws.width(1, 15);


            ws.range(0, 0, 0, 1).style().fontName("Arial").fontSize(16).bold().fillColor(Color.LIGHT_GREEN).merge().set();
            ws.value(0, 0, "Яркие");  // динам перем.
            ws.value(1, 0, "Число");
            ws.value(1, 1, "Тип заказа");
            ws.value(1, 2, "Имя файла");
            ws.value(1, 3, "Квадратура");

//            ws.range(2, 0, 2, 1).style().wrapText(true).set();
            int incr = 3;
            for (Map.Entry<String, Map<String, List<String>>> dayOfTheMonth : _dataResultMap.entrySet()) {
                String _dayOfTheMonth = dayOfTheMonth.getKey();
                for (Map.Entry<String, List<String>> typeWork : dayOfTheMonth.getValue().entrySet()) {
                    String _typeWork = typeWork.getKey();
                    for (String name : typeWork.getValue()) {
                        ws.value(incr, 0, _dayOfTheMonth);
                        ws.value(incr, 1, _typeWork);
                        ws.value(incr, 2, name);
                        ws.value(incr, 3, getSquareMeters(name));
                        incr++;
                    }
                }
            }
        }
    }

}
