package fa.training.dao;

import fa.training.entities.LineItem;
import fa.training.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static fa.training.common.SqlStatementConstant.*;

public class LineItemDAOImpl implements LineItemDAO {
    @Override
    public boolean addLineItem(LineItem item) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_LINEITEM);
            statement.setInt(1, item.getOrderId());
            statement.setInt(2, item.getProductId());
            statement.setInt(3, item.getQuantity());
            statement.setDouble(4, item.getPrice());

            int rowAffected = statement.executeUpdate();
            return rowAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
