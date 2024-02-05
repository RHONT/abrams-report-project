package com.abrams.reports;

import com.abrams.dto.GroupedCustomerOrder;
import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Worksheet;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GroupCustomerReport extends Report<GroupedCustomerOrder> {
    public GroupCustomerReport(String fileName, String DirectoryForSave) {
        super(DirectoryForSave, fileName);
    }

    @Override
    protected List<GroupedCustomerOrder> getListDto() {
        Optional<List<GroupedCustomerOrder>> groupedCustomerOrders = _dbService.selectGroupByTypeWork();
        return groupedCustomerOrders.orElseThrow(()->new NoSuchElementException("Files not found"));
    }

    @Override
    protected void fillTable(Collection<GroupedCustomerOrder> listDto, Worksheet sheet) {
        _currRowsIncrement = 3;
        for (var element : listDto) {
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
