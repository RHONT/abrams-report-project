package com.abrams.repository;

import com.abrams.etntity.Order;
import com.abrams.dto.GroupedOrderByTypeWork;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void createTable();
    Order save(Order order);
    void saveAll(List<Order> listOrders);
    Optional<List<Order>> selectAll();
    Optional<List<GroupedOrderByTypeWork>> selectGroupByTypeWork();

}
