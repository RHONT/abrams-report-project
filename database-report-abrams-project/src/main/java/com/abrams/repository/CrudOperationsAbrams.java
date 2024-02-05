package com.abrams.repository;


import com.abrams.dto.SingleCustomersOrder;
import com.abrams.dto.GroupedCustomerOrder;

import java.util.List;
import java.util.Optional;

public interface CrudOperationsAbrams {
    void createTable();
    void insertValue(SingleCustomersOrder singleCustomersOrder);
    Optional<List<SingleCustomersOrder>> selectAll();
    Optional<List<GroupedCustomerOrder>> selectGroupByTypeWork();

}
