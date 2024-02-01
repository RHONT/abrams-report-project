package com.abrams.dto;

import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.service.CrudOperationsOperationImpl;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CrudAbramsOperationServiceTest {

    @Test
    void createTable() {
    }

    @Test
    void insertValue() {
    }

    @Test
    void selectAll() {
        CrudOperationsAbrams crudOperations =new CrudOperationsOperationImpl();
        crudOperations.createTable();
        crudOperations.insertValue("1","10","500x500 1.pdf,");
        crudOperations.insertValue("1","10","1000x2000 1.pdf");
        crudOperations.insertValue("3","3","33");
        assertEquals(3, crudOperations.selectAll().get().size());
    }
}