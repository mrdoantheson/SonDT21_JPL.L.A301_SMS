package fa.training.dao;

import fa.training.entities.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getAllCustomers();
    void addCustomer(Customer customer);
    boolean deleteCustomer(int customerId);
    void updateCustomer(Customer customer);
}
