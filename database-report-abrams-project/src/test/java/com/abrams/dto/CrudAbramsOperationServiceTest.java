package com.abrams.dto;

import com.abrams.etntity.Order;
import com.abrams.repository.OrderRepository;
import com.abrams.service.OrderRepositoryImpl;
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
        Order customer=new Order(
                "01",
                "Poster",
                "test 340x333 1.pdf",
                0.33d);
        OrderRepository crudOperations =new OrderRepositoryImpl();
        crudOperations.createTable();
        crudOperations.save(customer);
        crudOperations.save(customer);
        crudOperations.save(customer);
        int expected=3;
        int actual=0;
        Optional<List<Order>> singleCustomersOrders = crudOperations.selectAll();
        if (singleCustomersOrders.isPresent()) {
            actual=singleCustomersOrders.get().size();
        }
        assertEquals(expected, actual);
    }
}