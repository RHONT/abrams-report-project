package com.abrams.dto;

import com.abrams.service.CrudOperationsOperationImpl;

/**
 * The object returned from the database by the method {@link CrudOperationsOperationImpl#selectGroupByTypeWork()}
 */
public class GroupedCustomerOrder {
    private final String _digitOfMonth;
    private final String _typeWork;
    private final double squareMeters;

    public GroupedCustomerOrder(String _digitOfMonth, String _typeWork, double squareMeters) {
        this._digitOfMonth = _digitOfMonth;
        this._typeWork = _typeWork;
        this.squareMeters = squareMeters;
    }

    public String getDigitOfMonth() {
        return _digitOfMonth;
    }

    public String getTypeWork() {
        return _typeWork;
    }

    public double getSquareMeters() {
        return squareMeters;
    }

    @Override
    public String toString() {
        return "RowObjectGroupByTypeWork{" +
                "_digitOfMonth='" + _digitOfMonth + '\'' +
                ", _typeWork='" + _typeWork + '\'' +
                ", squereMeters=" + squareMeters +
                '}';
    }
}
