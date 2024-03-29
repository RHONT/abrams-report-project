package com.abrams.reports;

import com.abrams.dto.OrderDto;
import com.abrams.etntity.Order;
import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Worksheet;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class SelectAllOrderReportExcel extends ReportExcel<Order> {
    public SelectAllOrderReportExcel(String fileName, String directoryForSave) {
        super(fileName + " Детальный", directoryForSave);
    }

    @Override
    protected List<Order> giveOrders() {
        Optional<List<Order>> groupedCustomerOrders = _dbService.selectAll();
        return groupedCustomerOrders.orElseThrow(() -> new NoSuchElementException("Files not found"));
    }

    @Override
    protected void fillTable(Collection<Order> entities, Worksheet sheet) {
        _currRowsIncrement = 3;
        for (var element : entities) {
            OrderDto dto=element.giveDto();
            sheet.value(_currRowsIncrement, 0, dto.get_digitOfMonth());
            sheet.value(_currRowsIncrement, 1, dto.get_typeWork());
            sheet.value(_currRowsIncrement, 2, dto.get_nameFile());
            sheet.value(_currRowsIncrement, 3, dto.get_squareMeters());
            _currRowsIncrement++;
        }
    }

    @Override
    protected void createHeadSheet(Worksheet sheet) {
        sheet.range(0, 0, 0, 1).style().fontName("Arial").
                fontSize(16).bold().fillColor(Color.LIGHT_LAUREL_GREEN).merge().set();
        sheet.value(0, 0, _fileName);  // динам перем.
        sheet.value(1, 0, "Число");
        sheet.value(1, 1, "Тип заказа");
        sheet.value(1, 2, "Квадратура");

    }
}
