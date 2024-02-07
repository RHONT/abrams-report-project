package com.abrams.outputto;

import com.abrams.etntity.ParentEntity;
import com.abrams.repository.OrderRepository;
import com.abrams.repository.OrderRepositoryImpl;

import java.util.Collection;
import java.util.NoSuchElementException;

public class WriterEntitiesToDB {
    private final String excMsg="list entities is empty";
    public WriterEntitiesToDB(Collection<? extends ParentEntity> list) {
        OrderRepository _dbService = new OrderRepositoryImpl();
        _dbService.createTable();
        writeToDB(list);
    }

    private void writeToDB(Collection<? extends ParentEntity> list) {
        if (list.isEmpty()) {
            throw new NoSuchElementException(excMsg);
        } else list.forEach(ParentEntity::save);
    }
}

