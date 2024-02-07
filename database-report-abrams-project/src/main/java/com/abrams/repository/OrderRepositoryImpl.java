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
    public boolean save(String digitOfMonth, String typeWork, String nameFile, double squareMeters) {
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(_insertQuery)) {
            pstmt.setString(1, digitOfMonth);
            pstmt.setString(2, typeWork);
            pstmt.setString(3, nameFile);
            pstmt.setDouble(4, squareMeters);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return true;
    }

    @Override
    public void saveAll(List<Order> listOrders) {
        listOrders.forEach(Order::save);
    }

    @Override
    public Optional<List<Order>> selectAll() {
        ResultSet resultSet;
        List<Order> orders = new ArrayList<>();
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(_selectQuery)) {
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String _digitOfMonth = resultSet.getString("digit_of_month");
                String _typeWork = resultSet.getString("type_work");
                String _fileName = resultSet.getString("name_file");
                double _squareMeters = resultSet.getDouble("square_meters");
                orders.add(
                        new Order(_digitOfMonth, _typeWork, _fileName, _squareMeters));
            }
            connection.createStatement().execute(_dropTable);
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return Optional.of(orders);
    }

    @Override
    public Optional<List<GroupedOrderByTypeWork>> selectGroupByTypeWork() {
        ResultSet resultSet;
        List<GroupedOrderByTypeWork> rowObjects = new ArrayList<>();
        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement pstmt = connection.prepareStatement(_selectGroupQuery)) {
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String _digitOfMonth = resultSet.getString("digit_of_month");
                String _typeWork = resultSet.getString("type_work");
                double _squareMeters = resultSet.getDouble("square_meters");
                rowObjects.add(new GroupedOrderByTypeWork(_digitOfMonth, _typeWork, _squareMeters));
            }
            statement.execute(_dropTable);
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return Optional.of(rowObjects);
    }
}
