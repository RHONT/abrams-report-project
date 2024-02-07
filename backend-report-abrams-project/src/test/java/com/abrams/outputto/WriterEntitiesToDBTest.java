package com.abrams.outputto;

import com.abrams.etntity.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WriterEntitiesToDBTest {

    private static List<Order> orders=new ArrayList<>();

    @BeforeAll
    static void init(){
        Order o1=new Order("11","plot cut","test4 100x100 1.pfd",0.01d);
        Order o2=new Order("11","plot cut","test5 100x100 1.pfd",0.01d);
        Order o3=new Order("11","plot cut","test6 100x100 1.pfd",0.01d);
        orders.addAll(List.of(o1,o2,o3));
    }

    @Test
    void emptyListThrowNoSuchException(){
        NoSuchElementException thrown=assertThrows(NoSuchElementException.class,()->new WriterEntitiesToDB(new ArrayList<>()));
        assertNotNull(thrown);
    }

    @Test
    void writeEntity(){
        assertDoesNotThrow(()->new WriterEntitiesToDB(orders));
    }

}