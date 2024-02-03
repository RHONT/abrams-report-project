package com.abrams.finder.creatorsreports;

import com.abrams.dto.SingleCustomersOrder;
import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Worksheet;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ExcelEachCustomer extends ExcelCreator<SingleCustomersOrder>{
    public ExcelEachCustomer(String fileName, String DirectoryForSave) {
        super(DirectoryForSave, fileName);
    }

    @Override
    protected List<SingleCustomersOrder> getListDto() {
        Optional<List<SingleCustomersOrder>> groupedCustomerOrders = _dbService.selectAll();
        return groupedCustomerOrders.orElseThrow(()->new NoSuchElementException("Files not found"));
    }

    @Override
    protected void fillTable(Collection<SingleCustomersOrder> listDto, Worksheet sheet) {
        _currRowsIncrement = 3;
        for (var element : listDto) {
            sheet.value(_currRowsIncrement, 0, element.get_digitOfMonth());
            sheet.value(_currRowsIncrement, 1, element.get_typeWork());
            sheet.value(_currRowsIncrement, 2, element.get_nameFile());
            sheet.value(_currRowsIncrement, 3, element.get_squareMeters());
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
