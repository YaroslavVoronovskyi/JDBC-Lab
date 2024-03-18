package com.vansisto.dao.impl;

import com.vansisto.config.MySQLConnector;
import com.vansisto.dao.ProductDAO;
import com.vansisto.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private static final String GET_QUERY = "SELECT * FROM product WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE product SET name = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM product WHERE id = ?";
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final int SECOND_COLUMN_INDEX = 2;
    private static final int THIRD_COLUMN_INDEX = 3;
    private static final int STATUS_CODE_IS_OK = 200;
    private static final int STATUS_CODE_IS_NOT_FOUND = 200;

    @Override
    public int create(Product product) {
        int status = 400;
        String SQL = "INSERT INTO product(name, price) VALUES (?, ?)";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());

            preparedStatement.execute();
            status = 201;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    @Override
    public Product getById(long id) {
        Product product = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
                if (id <= 0) {
                    throw new RuntimeException("Product ID must be positiv");
                }
                statement.setLong(FIRST_COLUMN_INDEX, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getLong(FIRST_COLUMN_INDEX));
                    product.setName(resultSet.getString(SECOND_COLUMN_INDEX));
                    product.setPrice(resultSet.getBigDecimal(THIRD_COLUMN_INDEX));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("SQLException exception in  getById(long id)", exception);
        }
        return product;
    }

    @Override
    public int update(Product product) {
        try (Connection connection = MySQLConnector.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
                statement.setString(FIRST_COLUMN_INDEX, product.getName());
                statement.setBigDecimal(SECOND_COLUMN_INDEX, product.getPrice());
                statement.setLong(THIRD_COLUMN_INDEX, product.getId());
                int result = statement.executeUpdate();
                if (result == 0) {
                    return STATUS_CODE_IS_NOT_FOUND;
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("SQLException exception in update(Product product)", exception);
        }
        return STATUS_CODE_IS_OK;
    }

    @Override
    public int deleteById(long id) {
        try (Connection connection = MySQLConnector.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
                statement.setLong(FIRST_COLUMN_INDEX, id);
                int result = statement.executeUpdate();
                if (result == 0) {
                    return STATUS_CODE_IS_NOT_FOUND;
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("SQLException exception in deleteById(long id)", exception);
        }
        return STATUS_CODE_IS_OK;
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();

        try (Connection connection = MySQLConnector.getConnection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");

                Product product = new Product(id, name, price);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
