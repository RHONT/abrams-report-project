package com.abrams.entity;

import com.abrams.config.H2JDBCUtils;
import com.abrams.operation.CrudOperation;

import java.sql.*;

public class H2Operation {

    private static final String _createTableSQL = "CREATE TABLE report (" +
            "digit_of_month  varchar(20) ," +
            "name_file varchar(20)," +
            "type_work varchar(20)," +
            "square_meters float)";

    private static final String _insertQuery = "INSERT INTO report (" +
            "digit_of_month, name_file,type_work,square_meters) values" + "(?,?,?,?)";

    private static final String _selectQuery = "select * from report";

    private static final String _selectCustomQuery = "select digit_of_month,type_work, sum(square_meters) from report group by digit_of_month";

    public static void main(String[] argv) throws SQLException {
        H2Operation createTableExample = new H2Operation();
        createTableExample.createTable();
    }

    public void createTable() throws SQLException {

//        System.out.println(createTableSQL);
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             Statement statement = connection.createStatement();) {

            connection.setAutoCommit(false);
            CrudOperation.createTable(statement);
//            statement.execute(_createTableSQL);
            PreparedStatement createPreparedStatement = null;
            PreparedStatement insertPreparedStatement = null;
            PreparedStatement selectPreparedStatement = null;

//             Step 3: Execute the query or update query
            insertPreparedStatement = connection.prepareStatement(_insertQuery);
            insertPreparedStatement.setString(1,"01");
            insertPreparedStatement.setString(2,"Пленка");
            insertPreparedStatement.setString(3,"Good.pdf");
            insertPreparedStatement.setDouble(4,0.5d);
            insertPreparedStatement.executeUpdate();

            insertPreparedStatement.setString(1,"02");
            insertPreparedStatement.setString(2,"Пленка");
            insertPreparedStatement.setString(3,"Bad.pdf");
            insertPreparedStatement.setDouble(4,5d);
            insertPreparedStatement.executeUpdate();

            insertPreparedStatement.setString(1,"02");
            insertPreparedStatement.setString(2,"Пленка");
            insertPreparedStatement.setString(3,"Bad.pdf");
            insertPreparedStatement.setDouble(4,1d);
            insertPreparedStatement.executeUpdate();

            insertPreparedStatement.close();

            selectPreparedStatement= connection.prepareStatement(_selectQuery);
            ResultSet resultSet = selectPreparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.printf("%s %s %s %f%n", resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4));
            }
            resultSet.close();
            selectPreparedStatement.close();
            System.out.println("----====");

            selectPreparedStatement= connection.prepareStatement(_selectCustomQuery);
            resultSet=selectPreparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.printf("%s %s %f%n", resultSet.getString(1),
                        resultSet.getString(2), resultSet.getDouble(3));
            }

        } catch (SQLException e) {
            // print SQL exception information
            H2JDBCUtils.printSQLException(e);
        }
    }
}
