package com.abrams.repository;

import com.abrams.config.H2JDBCUtils;
import com.abrams.etntity.Order;
import com.abrams.dto.GroupedOrderByTypeWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    private static final String _dropTable = "drop table report";
    private static final String _createTableSQL = "" +
            "CREATE TABLE IF NOT EXISTS report (" +
            "digit_of_month  varchar," +
            "type_work varchar," +
            "name_file varchar PRIMARY KEY not null ," +
            "square_meters double)";
    private static final String _selectQuery = "select * from report";

    private static final String _insertQuery = "" +
            "INSERT INTO report (" +
            "digit_of_month,type_work, name_file,square_meters) values" + "(?,?,?,?)";

    private static final String _selectGroupQuery = "" +
            "select digit_of_month,type_work, sum(square_meters) " +
            "from report " +
            "group by digit_of_month,type_work";

    @Override
    public void createTable() {
        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(_createTableSQL);
            connection.commit();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
    }

    @Override
    public Order save(Order customer) {
        PreparedStatement insertPreparedStatement;

        try (Connection connection = H2JDBCUtils.getConnection()) {

            insertPreparedStatement = connection.prepareStatement(_insertQuery);
            insertPreparedStatement.setString(1, customer.get_digitOfMonth());
            insertPreparedStatement.setString(2, customer.get_typeWork());
            insertPreparedStatement.setString(3, customer.get_nameFile());
            insertPreparedStatement.setDouble(4, customer.get_squareMeters());
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return customer;
    }

    @Override
    public void saveAll(List<Order> listOrders) {
        PreparedStatement insertPreparedStatement;
        try (Connection connection = H2JDBCUtils.getConnection()) {
            connection.setAutoCommit(false);
            insertPreparedStatement = connection.prepareStatement(_insertQuery);
            for (int i = 0; i < listOrders.size(); i++) {
                    Order order = listOrders.get(i);
                    insertPreparedStatement.setString(1, order.get_digitOfMonth());
                    insertPreparedStatement.setString(2, order.get_typeWork());
                    insertPreparedStatement.setString(3, order.get_nameFile());
                    insertPreparedStatement.setDouble(4, order.get_squareMeters());
                    insertPreparedStatement.addBatch();
            }
            try {
                insertPreparedStatement.executeBatch();
                connection.commit();
            } catch (BatchUpdateException e) {
                connection.rollback();
                throw new RuntimeException("error with group save orders" + e.getMessage());
            }
            insertPreparedStatement.close();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }

    }

    @Override
    public Optional<List<Order>> selectAll() {
        ResultSet resultSet;
        List<Order> orders = new ArrayList<>();
        PreparedStatement selectPreparedStatement;

        try (Connection connection = H2JDBCUtils.getConnection()) {

            selectPreparedStatement = connection.prepareStatement(_selectQuery);
            resultSet = selectPreparedStatement.executeQuery();

                while (resultSet.next()) {
                    String _digitOfMonth = resultSet.getString(1);
                    String _typeWork = resultSet.getString(2);
                    String _fileName = resultSet.getString(3);
                    double _squareMeters = resultSet.getDouble(4);
                    orders.add(
                            new Order(_digitOfMonth, _typeWork, _fileName, _squareMeters));
                }
                connection.createStatement().execute(_dropTable);
            connection.commit();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return Optional.of(orders);
    }

    @Override
    public Optional<List<GroupedOrderByTypeWork>> selectGroupByTypeWork() {
        ResultSet resultSet;
        List<GroupedOrderByTypeWork> rowObjects = new ArrayList<>();
        PreparedStatement selectPreparedStatement;

        try (Connection connection = H2JDBCUtils.getConnection()) {

            selectPreparedStatement = connection.prepareStatement(_selectGroupQuery);
            resultSet = selectPreparedStatement.executeQuery();

            while (resultSet.next()) {
                String _digitOfMonth = resultSet.getString("digit_of_month");
                String _typeWork = resultSet.getString("type_work");
                double _squareMeters = resultSet.getDouble(3);
                rowObjects.add(new GroupedOrderByTypeWork(_digitOfMonth, _typeWork, _squareMeters));
            }
            connection.createStatement().execute(_dropTable);
            connection.commit();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return Optional.of(rowObjects);
    }
}
