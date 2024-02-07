package com.abrams.etntity;

import  com.abrams.repository.OrderRepository;
import com.abrams.repository.OrderRepositoryImpl;

/**
 * The DTO returned from the database by the method {@link OrderRepositoryImpl#selectAll()}
 */
public class Order extends ParentEntity {
    OrderRepository operation = new OrderRepositoryImpl();
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

    public String get_nameFile() {
        return _nameFile;
    }

    public String get_digitOfMonth() {
        return _digitOfMonth;
    }

    public String get_typeWork() {
        return _typeWork;
    }

    public double get_squareMeters() {
        return _squareMeters;
    }

    @Override
    public Order save() {
        return operation.save(this);
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
