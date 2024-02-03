package com.abrams;

import com.abrams.finder.creatorsreports.ExcelGroup;
import com.abrams.finder.search.FinderDirectoriesByName;
import com.abrams.finder.search.FinderFiles;
import com.abrams.finder.write.WriterToDB;
import com.abrams.service.CrudOperationsOperationImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UiFrameTest {

    @Test
    void groupSaveOperationOk() throws IOException {
        new WriterToDB(new FinderFiles(new FinderDirectoriesByName("Яркие","C:/labaratory/Январь")));
        assertTrue(new ExcelGroup("Яркие","C:/labaratory/Январь").save());
    }

}