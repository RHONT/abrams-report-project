package com.abrams.entity;

import com.abrams.service.CrudAbramsOperationService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


class CrudAbramsOperationServiceTest {

    @Test
    void createTable() {
    }

    @Test
    void insertValue() {
    }

    @Test
    void selectAll() throws SQLException {
        CrudAbramsOperationService crudAbramsOperationService =new CrudAbramsOperationService();
        crudAbramsOperationService.createTable();
        crudAbramsOperationService.insertValue("1","10","500x500 1.pdf,");
        crudAbramsOperationService.insertValue("1","10","1000x2000 1.pdf");
        crudAbramsOperationService.insertValue("3","3","33");
        System.out.println(crudAbramsOperationService.selectGroupByTypeWork().get());
//        System.out.println(h2Operation.selectAll().get());
    }
}