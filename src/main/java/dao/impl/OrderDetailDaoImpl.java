package dao.impl;

import dao.custom.OrderDetailDao;
import dao.util.SqlUtil;
import entity.FoodOrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean delete(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean update(FoodOrderDetails entity) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public boolean add(FoodOrderDetails entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO foodOrderDetail(orderId , foodId   , qty   ,amount  ,date ) VALUES(?, ?, ?, ?, ?)", entity.getOrderID(), entity.getFoodID(), entity.getQty(), entity.getAmount(), entity.getDate());
    }

    @Override
    public FoodOrderDetails search(String s) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<FoodOrderDetails> getAll() throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM foodorderDetails");
        ArrayList<FoodOrderDetails> allOrderDetails = new ArrayList<>();
        while (rst.next()) {
            allOrderDetails.add(new FoodOrderDetails(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4), rst.getString(5)));
        }
        return allOrderDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }
}
