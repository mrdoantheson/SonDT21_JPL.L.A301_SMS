package fa.training.main;

import fa.training.dao.*;
import fa.training.entities.Customer;
import fa.training.entities.LineItem;import fa.training.entities.Order;

import java.util.List;
import java.util.Scanner;

public class SaleManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        LineItemDAO lineItemDAO = new LineItemDAOImpl();
        int choice;
        boolean running = true;
        do {
            printMenu();
            System.out.print("Your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println();
                    System.out.println("--------- List all customers in the database ---------");
                    List<Customer> customerList = customerDAO.getAllCustomers();
                    customerList.forEach(System.out::println);
                    System.out.println();
                }
                case 2 -> {
                    System.out.print("Enter customer ID: ");
                    int customerId = scanner.nextInt();

                    System.out.println();
                    System.out.println("--------- List all orders by customer id in the database ---------");
                    orderDAO.getAllOrdersByCustomerId(customerId).forEach(System.out::println);
                    System.out.println();
                }
                case 3 -> {
                    System.out.println("--------- List all items ---------");

                    System.out.print("Enter order ID: ");
                    int orderId = scanner.nextInt();

                    System.out.println();
                    orderDAO.getAllItemsByOrderId(orderId).forEach(System.out::println);
                    System.out.println();
                }
                case 4 -> {
                    System.out.println("--------- Compute order total ---------");

                    System.out.print("Enter order ID: ");
                    int orderId = scanner.nextInt();

                    System.out.println();
                    System.out.println(orderDAO.computeOrderTotal(orderId));
                    System.out.println();
                }
                case 5 -> {
                    System.out.println("--------- Insert a customer ---------");

                    System.out.print("Enter name: ");
                    scanner = new Scanner(System.in);
                    String customerName = scanner.nextLine();
                    Customer customer = new Customer(customerName);
                    customerDAO.addCustomer(customer);
                    System.out.println("--------- Insert success ---------");
                }
                case 6 -> {
                    System.out.println("--------- Delete a customer ---------");

                    System.out.println("Enter customer ID: ");
                    int customerId = scanner.nextInt();

                    System.out.println("Delete customer: " + customerDAO.deleteCustomer(customerId));
                }
                case 7 -> {
                    System.out.println("--------- Update a customer ---------");
                    System.out.print("Enter the id customer you want to update : ");
                    int customerID = scanner.nextInt();

                    scanner = new Scanner(System.in);
                    System.out.print("Enter the name customer you want to update : ");
                    String customerName = scanner.nextLine();

                    Customer customer = new Customer(customerID, customerName);
                    customerDAO.updateCustomer(customer);

                    System.out.println("--------- Update success ---------");
                }
                case 8 -> {
                    System.out.println("--------- Insert a order ---------");

                    System.out.print("Enter customer ID: ");
                    int customerID = scanner.nextInt();

                    System.out.print("Enter employee ID: ");
                    int employeeID = scanner.nextInt();

                    System.out.print("Enter total: ");
                    int total = scanner.nextInt();

                    orderDAO.addOrder(new Order(customerID, employeeID, total));

                    System.out.println("--------- Insert success ---------");
                }
                case 9 -> {
                    System.out.println("--------- Insert an order ---------");

                    System.out.print("Enter order id: ");
                    scanner = new Scanner(System.in);
                    int orderId = scanner.nextInt();

                    System.out.print("Enter product id: ");
                    int productId = scanner.nextInt();

                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();

                    System.out.print("Enter price: ");
                    int price = scanner.nextInt();

                    lineItemDAO.addLineItem(new LineItem(orderId, productId, quantity, price));

                    System.out.println("--------- Insert success ---------");
                }
                case 10 -> System.out.println("Update total: " + orderDAO.updateOrderTotal(6));
                case 11 -> running = false;
                default -> System.out.println("Please input number in range 0 - 10");
            }
        } while (running);
    }

    private static void printMenu() {
        System.out.println("******************************** Start Menu ********************************");
        System.out.println("1. List all customers in the database");
        System.out.println("2. List all orders in the database");
        System.out.println("3. List all items for an order, for a given order id");
        System.out.println("4. Compute order total, returns a list with a row containing the computed order total from the line items ");
        System.out.println("5. Add a customer into the database");
        System.out.println("6. Delete a customer from the database, make sure to also delete Orders and LineItem for the deleted customer");
        System.out.println("7. Update a customer in the database");
        System.out.println("8. Create an order into the database");
        System.out.println("9. Create a lineitem into the database");
        System.out.println("10. Update an order total into the database");
        System.out.println("11. Exit program");

    }
}
