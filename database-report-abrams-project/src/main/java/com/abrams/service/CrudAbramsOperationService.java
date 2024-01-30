package com.abrams.service;

import com.abrams.dto.RowObject;
import com.abrams.dto.RowObjectGroupByTypeWork;
import com.abrams.repository.CrudOperationsAbrams;
import com.abrams.repository.CrudOperationsOperationImpl;

import java.util.List;
import java.util.Optional;


public class CrudAbramsOperationService implements CrudOperationsAbrams {
    CrudOperationsAbrams crudOperationsAbrams;

    public CrudAbramsOperationService() {
        this.crudOperationsAbrams = new CrudOperationsOperationImpl();
    }

    @Override
    public void createTable() {
        crudOperationsAbrams.createTable();
    }

    @Override
    public void insertValue(String digit_of_month, String type_work, String name_file) {
        crudOperationsAbrams.insertValue(digit_of_month, type_work, name_file);
    }

    @Override
    public Optional<List<RowObject>> selectAll() {
        return crudOperationsAbrams.selectAll();
    }

    @Override
    public Optional<List<RowObjectGroupByTypeWork>> selectGroupByTypeWork() {
        return crudOperationsAbrams.selectGroupByTypeWork();
    }
}
