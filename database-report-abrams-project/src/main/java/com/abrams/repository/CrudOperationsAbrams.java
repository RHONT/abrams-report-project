package com.abrams.repository;


import com.abrams.dto.SingleCustomersOrder;
import com.abrams.dto.GroupedCustomerOrder;

import java.util.List;
import java.util.Optional;

public interface CrudOperationsAbrams {
    void createTable();
    void insertValue(String digit_of_month,String type_work, String name_file);
    Optional<List<SingleCustomersOrder>> selectAll();
    Optional<List<GroupedCustomerOrder>> selectGroupByTypeWork();

}
