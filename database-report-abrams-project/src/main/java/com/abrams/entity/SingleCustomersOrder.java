package com.abrams.entity;

import com.abrams.service.CrudOperationsOperationImpl;

/**
 * The object returned from the database by the method {@link CrudOperationsOperationImpl#selectAll()}
 */
public class SingleCustomersOrder {
    private final String _digitOfMonth;
    private final String _typeWork;
    private final String _nameFile;
    private final double _squareMeters;

    public SingleCustomersOrder(String _digitOfMonth, String _typeWork, String _nameFile, double _squareMeters) {
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
    public String toString() {
        return "RowObject{" +
                "_digitOfMonth='" + _digitOfMonth + '\'' +
                ", _typeWork='" + _typeWork + '\'' +
                ", _nameFile='" + _nameFile + '\'' +
                ", _squareMeters=" + _squareMeters +
                '}';
    }
}
