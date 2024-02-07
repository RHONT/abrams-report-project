package com.abrams.reports;

import com.abrams.dto.GroupedOrderByTypeWork;
import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Worksheet;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GroupCustomerReportExcel extends ReportExcel<GroupedOrderByTypeWork> {
    public GroupCustomerReportExcel(String fileName, String directoryForSave) {
        super(fileName + " Групповой", directoryForSave);
    }

    @Override
    protected List<GroupedOrderByTypeWork> giveOrders() {
        Optional<List<GroupedOrderByTypeWork>> groupedCustomerOrders = _dbService.selectGroupByTypeWork();
        return groupedCustomerOrders.orElseThrow(() -> new NoSuchElementException("Files not found"));
    }

    @Override
    protected void fillTable(Collection<GroupedOrderByTypeWork> entities, Worksheet sheet) {
        _currRowsIncrement = 3;
        for (var element : entities) {
            sheet.value(_currRowsIncrement, 0, element.getDigitOfMonth());
            sheet.value(_currRowsIncrement, 1, element.getTypeWork());
            sheet.value(_currRowsIncrement, 2, element.getSquareMeters());
            _currRowsIncrement++;
        }

    }

    @Override
    protected void createHeadSheet(Worksheet sheet) {
        sheet.range(0, 0, 0, 1).style().fontName("Arial").
                fontSize(16).bold().fillColor(Color.LIGHT_PASTEL_BLUE).merge().set();
        sheet.value(0, 0, _fileName);  // динам перем.
        sheet.value(1, 0, "Число");
        sheet.value(1, 1, "Тип заказа");
        sheet.value(1, 2, "Квадратура");

    }
}
