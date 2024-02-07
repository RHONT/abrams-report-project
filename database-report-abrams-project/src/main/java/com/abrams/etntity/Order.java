package com.abrams.etntity;

import com.abrams.dto.OrderDto;
import  com.abrams.repository.OrderRepository;
import com.abrams.repository.OrderRepositoryImpl;

public class Order extends ParentEntity {
    OrderRepository orderRepository = new OrderRepositoryImpl();
    private final String _digitOfMonth;
    private final String _typeWork;
    private final String _nameFile;
    private final double _squareMeters;

    public Order(String _digitOfMonth, String _typeWork, String _nameFile, double _squareMeters) {
        this._digitOfMonth = _digitOfMonth;
        this._typeWork = _typeWork;
        this._nameFile = _nameFile;
        this._squareMeters = _squareMeters;
    }

    @Override
    public boolean save() {
        return orderRepository.save(_digitOfMonth,_typeWork,_nameFile,_squareMeters);
    }

    public OrderDto giveDto(){
        return new OrderDto(_digitOfMonth,_typeWork,_nameFile,_squareMeters);
    }

    @Override
    public String toString() {
        return "RowObject{" +
                "_digitOfMonth='" + _digitOfMonth + '\'' +
                ", _typeWork='" + _typeWork + '\'' +
                ", _nameFile='" + _nameFile + '\'' +
                ", _squareMeters=" + _squareMeters +
                '}';
    }
}
