package com.abrams.operation;

import com.abrams.config.H2JDBCUtils;

import java.sql.*;

public class CrudOperation {
    private static final String _createTableSQL = "CREATE TABLE report (" +
            "digit_of_month  varchar(20) ," +
            "name_file varchar(20)," +
            "type_work varchar(20)," +
            "square_meters float)";

    private static final String _insertQuery = "INSERT INTO report (" +
            "digit_of_month, name_file,type_work,square_meters) values" + "(?,?,?,?)";

    private static final String _selectQuery = "select * from report";

    private static final String _selectCustomQuery = "select digit_of_month,type_work, sum(square_meters) from report group by digit_of_month";

    public static void createTable(Statement statement) throws SQLException {
             statement.execute(_createTableSQL);
    }
}
