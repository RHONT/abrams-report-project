package com.abrams.dto;

public class OrderDto {
    private final String _digitOfMonth;
    private final String _typeWork;
    private final String _nameFile;
    private final double _squareMeters;

    public OrderDto(String _digitOfMonth, String _typeWork, String _nameFile, double _squareMeters) {
        this._digitOfMonth = _digitOfMonth;
        this._typeWork = _typeWork;
        this._nameFile = _nameFile;
        this._squareMeters = _squareMeters;
    }

    public String get_digitOfMonth() {
        return _digitOfMonth;
    }

    public String get_typeWork() {
        return _typeWork;
    }

    public String get_nameFile() {
        return _nameFile;
    }

    public double get_squareMeters() {
        return _squareMeters;
    }
}
