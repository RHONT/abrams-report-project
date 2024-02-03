package com.abrams;

import com.abrams.finder.reports.selectAllCustomerReport;
import com.abrams.finder.reports.groupCustomerReport;
import com.abrams.finder.search.FinderDirectoriesByName;
import com.abrams.finder.search.FinderFiles;
import com.abrams.finder.write.WriterToDB;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UiFrameTest {

    @Test
    void groupSaveOperationOk() throws IOException {
        new WriterToDB(new FinderFiles(new FinderDirectoriesByName("Яркие","C:/labaratory/Январь")));
        assertTrue(new groupCustomerReport("Яркие","C:/labaratory/Январь").save());
    }

    @Test
    void selectAllOperationOk() throws IOException {
        new WriterToDB(new FinderFiles(new FinderDirectoriesByName("Яркие","C:/labaratory/Январь")));
        assertTrue(new selectAllCustomerReport("Яркие","C:/labaratory/Январь").save());
    }

}