package dao.impl;

import dao.custom.OrderDao;
import dao.util.SqlUtil;
import entity.FoodOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean delete(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean update(FoodOrder entity) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean add(FoodOrder entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO foodOrders(orderId, date , bookingId ) VALUES(?, ?, ?)", entity.getOrderId(), entity.getDate(), entity.getBookingId());
    }

    @Override
    public FoodOrder search(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<FoodOrder> getAll() throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM foodorders");
        ArrayList<FoodOrder> allOrders = new ArrayList<>();
        while (rst.next()) {
            allOrders.add(new FoodOrder(rst.getString(1), rst.getString(2), rst.getString(3)));
        }
        return allOrders;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT orderId FROM foodOrders ORDER BY orderId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("orderId");
            int newOrderId = Integer.parseInt(id.replace("O00-", "")) + 1;
            return String.format("O00-%03d", newOrderId);
        } else {
            return "O00-001";
        }
    }
}
