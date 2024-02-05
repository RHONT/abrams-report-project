package com.abrams.dto;

import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.service.CrudOperationsOperationImpl;
import org.junit.jupiter.api.*;


import java.util.List;
import java.util.Optional;

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
        SingleCustomersOrder customer=new SingleCustomersOrder(
                "01",
                "Poster",
                "test 340x333 1.pdf",
                0.33d);
        CrudOperationsAbrams crudOperations =new CrudOperationsOperationImpl();
        crudOperations.createTable();
        crudOperations.insertValue(customer);
        crudOperations.insertValue(customer);
        crudOperations.insertValue(customer);
        int expected=3;
        int actual=0;
        Optional<List<SingleCustomersOrder>> singleCustomersOrders = crudOperations.selectAll();
        if (singleCustomersOrders.isPresent()) {
            actual=singleCustomersOrders.get().size();
        }
        assertEquals(expected, actual);
    }
}