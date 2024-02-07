package com.abrams.dto;

import com.abrams.etntity.Order;
import com.abrams.repository.OrderRepository;
import com.abrams.repository.OrderRepositoryImpl;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CrudAbramsOperationServiceTest {
    private static List<Order> orders=new ArrayList<>();
    private static Order o1=new Order("11","plot cut","test 100x100 1.pfd",0.01d);
    private static Order o2=new Order("11","plot cut","test1 100x100 1.pfd",0.01d);
    private static Order o3=new Order("11","plot cut","test2 100x100 1.pfd",0.01d);

    @BeforeAll
    static void init(){
        orders.addAll(List.of(o1,o2,o3));
    }
    @Test
    void createTable() {
    }

    @Test
    void insertValue() {
    }

    @Test
    void selectAll() {
        OrderRepository crudOperations =new OrderRepositoryImpl();
        crudOperations.createTable();
        crudOperations.save(o1);
        crudOperations.save(o2);
        crudOperations.save(o3);
        int expected=3;
        int actual=0;
        Optional<List<Order>> singleCustomersOrders = crudOperations.selectAll();
        if (singleCustomersOrders.isPresent()) {
            actual=singleCustomersOrders.get().size();
        }
        assertEquals(expected, actual);
    }
}