package com.abrams.repository;


import com.abrams.dto.RowObject;
import com.abrams.dto.RowObjectGroupByTypeWork;

import java.util.List;
import java.util.Optional;

public interface CrudOperationsAbrams {
    void createTable();
    void insertValue(String digit_of_month,String type_work, String name_file);
    Optional<List<RowObject>> selectAll();
    Optional<List<RowObjectGroupByTypeWork>> selectGroupByTypeWork();

}
