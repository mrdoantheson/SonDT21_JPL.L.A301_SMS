package fa.training.common;

public class SqlStatementConstant {
    public static final String SELECT_GET_ALL_CUSTOMER_HAS_PURCHASED =
            """
SELECT c.customer_id, c.customer_name FROM Customer c
INNER JOIN `Order` o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name;""";

    public static final String SELECT_GET_ALL_ORDER_BY_CUSTOMER_ID =
            """
SELECT o.order_id, o.order_date, o.customer_id, o.employee_id, o.total
FROM `Order` o
WHERE o.customer_id = ?;""";

    public static final String SELECT_GET_ALL_ITEMS_BY_ORDER_ID =
            "SELECT i.order_id, i.product_id, i.quantity, i.price\n" +
                    "FROM LineItem i WHERE I.order_id = ?;";

    public static final String SELECT_COMPUTE_ORDER_TOTAL =
            """
SELECT SUM(I.price * i.quantity) AS total_price
FROM LineItem i WHERE I.order_id = ?
GROUP BY i.order_id;""";

    public static final String DELETE_CUSTOMER_BY_ID =
            "CALL usp_Customer_deleteById (?);";

    public static final String UPDATE_ORDER_TOTAL =
            """
UPDATE `Order` SET total =
    (SELECT SUM(I.price * i.quantity) AS total_price
    FROM LineItem i WHERE I.order_id = ?
    GROUP BY i.order_id
    ) WHERE order_id = ?;""";

    public static final String ADD_CUSTOMER =
            "INSERT INTO Customer(customer_name) VALUES (?);";

    public static final String ADD_LINEITEM =
            "INSERT INTO LineItem (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?);";

    public static final String UPDATE_CUSTOMER =
            "UPDATE Customer SET customer_name = ? WHERE customer_id = ?;";

    public static final String ADD_ORDER =
            "INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), ?, ?, ?);";
}
