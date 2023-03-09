package fa.training.dao;

import fa.training.entities.LineItem;
import fa.training.entities.Order;
import fa.training.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static fa.training.common.SqlStatementConstant.*;

public class OrderDAOImpl implements OrderDAO {

    /**
     * List all orders consist of order id, order date, customer id, employee id, total for a customer
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Order> getAllOrdersByCustomerId(int customerId) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_GET_ALL_ORDER_BY_CUSTOMER_ID);
            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("order_id");
                Date orderDate = rs.getTimestamp("order_date");
                Double total = rs.getDouble("total");
                orders.add(new Order(id, orderDate, customerId, customerId, total));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<LineItem> getAllItemsByOrderId(int orderId) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_GET_ALL_ITEMS_BY_ORDER_ID);
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            List<LineItem> lineItems = new ArrayList<>();
            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                Double price = rs.getDouble("price");
                lineItems.add(new LineItem(orderId, product_id, quantity, price));
            }
            return lineItems;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Double computeOrderTotal(int orderId) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_COMPUTE_ORDER_TOTAL);
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateOrderTotal(int orderId) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_TOTAL);
            statement.setInt(1, orderId);
            statement.setInt(2, orderId);
            int isUpdate = statement.executeUpdate();

            return isUpdate >= 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addOrder(Order order) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_ORDER);
            statement.setInt(1, order.getCustomerId());
            statement.setInt(2, order.getEmployeeId());
            statement.setInt(3, order.getEmployeeId());
            statement.setDouble(4, order.getTotal());

            int rowAffected = statement.executeUpdate();
            return rowAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
