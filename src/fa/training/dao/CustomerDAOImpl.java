package fa.training.dao;

import fa.training.entities.Customer;
import fa.training.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fa.training.common.SqlStatementConstant.*;

/**
 * @author SonDT21
 */
public class CustomerDAOImpl implements CustomerDAO {

    /**
     * List all customers consist of customer id, customer name in the database
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Customer> getAllCustomers() {
        try (Connection connection = DBUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_GET_ALL_CUSTOMER_HAS_PURCHASED);
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("customer_id"), rs.getString("customer_name")));
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void addCustomer(Customer customer) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
            statement.setString(1, customer.getCustomerName());

            int rowAffected = statement.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
        }

    @Override
    public boolean deleteCustomer(int customerId) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_BY_ID);
            statement.setInt(1, customerId);
            return statement.executeUpdate() >= 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer == null || customer.getCustomerId() == 0) {
            throw new IllegalArgumentException("Customer is invalid");
        }
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
            statement.setString(1, customer.getCustomerName());
            statement.setInt(2, customer.getCustomerId());

            int rowAffected = statement.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }
        }
}