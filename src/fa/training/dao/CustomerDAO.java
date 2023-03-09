package fa.training.dao;

import fa.training.entities.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    List<Customer> getAllCustomers();
    boolean addCustomer(Customer customer);
    boolean deleteCustomer(int customerId);
    boolean updateCustomer(Customer customer);
}
