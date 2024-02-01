package com.abrams.entity;

import com.abrams.service.CrudOperationsOperationImpl;

/**
 * The object returned from the database by the method {@link CrudOperationsOperationImpl#selectGroupByTypeWork()}
 */
public class GroupedCustomerOrder {
    private final String _digitOfMonth;
    private final String _typeWork;
    private final double squereMeters;

    public GroupedCustomerOrder(String _digitOfMonth, String _typeWork, double squereMeters) {
        this._digitOfMonth = _digitOfMonth;
        this._typeWork = _typeWork;
        this.squereMeters = squereMeters;
    }

    public String get_digitOfMonth() {
        return _digitOfMonth;
    }

    public String get_typeWork() {
        return _typeWork;
    }

    public double getSquereMeters() {
        return squereMeters;
    }

    @Override
    public String toString() {
        return "RowObjectGroupByTypeWork{" +
                "_digitOfMonth='" + _digitOfMonth + '\'' +
                ", _typeWork='" + _typeWork + '\'' +
                ", squereMeters=" + squereMeters +
                '}';
    }
}
