package fa.training.dao;

import fa.training.entities.LineItem;
import fa.training.entities.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> getAllOrdersByCustomerId(int customerId);
    List<LineItem> getAllItemsByOrderId(int orderId);
    Double computeOrderTotal(int orderId);
    boolean updateOrderTotal(int orderId);
    boolean addOrder(Order order);
}

