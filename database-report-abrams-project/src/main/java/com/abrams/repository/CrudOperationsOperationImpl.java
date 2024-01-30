package com.abrams.repository;

import com.abrams.config.H2JDBCUtils;
import com.abrams.dto.RowObject;
import com.abrams.dto.RowObjectGroupByTypeWork;
import com.abrams.finder.UtilClass;
import com.abrams.repository.CrudOperationsAbrams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrudOperationsOperationImpl implements CrudOperationsAbrams {
    private static final String _createTableSQL = "CREATE TABLE report (" +
            "digit_of_month  varchar(20)," +
            "type_work varchar(20)," +
            "name_file varchar(20)," +
            "square_meters double)";
    private static final String _selectQuery = "select * from report";

    private static final String _insertQuery = "INSERT INTO report (" +
            "digit_of_month,type_work, name_file,square_meters) values" + "(?,?,?,?)";

    private static final String _selectCustomQuery = "select digit_of_month,type_work, sum(square_meters) from report group by digit_of_month,type_work";

    @Override
    public void createTable() {
        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {

            boolean check = statement.execute(_createTableSQL);
            System.out.println("Таблица создана? = " + check);
            connection.commit();

        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
    }

    @Override
    public void insertValue(String digit_of_month, String type_work, String name_file) {
        PreparedStatement insertPreparedStatement;

        try (Connection connection = H2JDBCUtils.getConnection();) {

            insertPreparedStatement = connection.prepareStatement(_insertQuery);
            insertPreparedStatement.setString(1, digit_of_month);
            insertPreparedStatement.setString(2, type_work);
            insertPreparedStatement.setString(3, name_file);
            insertPreparedStatement.setDouble(4, UtilClass.getSquareMeters(name_file));
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
    }

    @Override
    public Optional<List<RowObject>> selectAll() {
        ResultSet resultSet;
        List<RowObject> rowObjects = new ArrayList<>();
        PreparedStatement selectPreparedStatement;

        try (Connection connection = H2JDBCUtils.getConnection();) {

            selectPreparedStatement = connection.prepareStatement(_selectQuery);
            resultSet = selectPreparedStatement.executeQuery();
            while (resultSet.next()) {
                String _digitOfMonth = resultSet.getString(1);
                String _typeWork = resultSet.getString(2);
                String _fileName = resultSet.getString(3);
                double _squareMeters = resultSet.getDouble(4);
                rowObjects.add(
                        new RowObject(_digitOfMonth, _typeWork, _fileName, _squareMeters));
            }
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return Optional.of(rowObjects);
    }

    @Override
    public Optional<List<RowObjectGroupByTypeWork>> selectGroupByTypeWork() {
        ResultSet resultSet;
        List<RowObjectGroupByTypeWork> rowObjects = new ArrayList<>();
        PreparedStatement selectPreparedStatement;

        try (Connection connection = H2JDBCUtils.getConnection();) {

            selectPreparedStatement = connection.prepareStatement(_selectCustomQuery);
            resultSet = selectPreparedStatement.executeQuery();

            while (resultSet.next()) {
                String _digitOfMonth=resultSet.getString("digit_of_month");
                String _typeWork=resultSet.getString("type_work");
                double _squareMeters= resultSet.getDouble(3);
                rowObjects.add(new RowObjectGroupByTypeWork(_digitOfMonth,_typeWork,_squareMeters));
            }
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return Optional.of(rowObjects);
    }
}
