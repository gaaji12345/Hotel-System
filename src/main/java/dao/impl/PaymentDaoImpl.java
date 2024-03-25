package dao.impl;

import dao.custom.PaymentDao;
import dao.util.SqlUtil;
import entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public boolean delete(String id) throws SQLException {
        return SqlUtil.execute("DELETE FROM Payment WHERE paymentId = ?", id);
    }

    @Override
    public boolean update(Payment entity) throws SQLException {
        return SqlUtil.execute("UPDATE Payment SET guestId = ?, guestName = ?, bookingId = ?, roomId = ?, checkInDate = ?, checkOutDate = ?, ordersAmount = ?, totalPrice = ? WHERE paymentId = ?", entity.getGuestId(), entity.getGuestName(), entity.getBookingId(), entity.getRoomId(), entity.getCheckInDate(), entity.getCheckOutDate(), entity.getOrdersAmount(), entity.getTotalPrice(), entity.getPaymentId());
    }

    @Override
    public boolean add(Payment entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO Payment(paymentId , guestId  , guestName  , bookingId  , roomId  ,checkInDate  ,checkOutDate  ,ordersAmount  , totalPrice  ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", entity.getPaymentId(), entity.getGuestId(), entity.getGuestName(), entity.getBookingId(), entity.getRoomId(), entity.getCheckInDate(), entity.getCheckOutDate(), entity.getOrdersAmount(), entity.getTotalPrice());
    }

    @Override
    public Payment search(String id) throws SQLException {
        throw new UnsupportedOperationException("This feature yet to be developed");
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException {
        ArrayList<Payment> allPaymentDetails = new ArrayList<>();
        ResultSet rst = SqlUtil.execute("SELECT * FROM Payment");
        while (rst.next()) {
            allPaymentDetails.add(new Payment(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getDouble(8), rst.getDouble(9)));
        }
        return allPaymentDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("paymentId");
            int newPaymentId = Integer.parseInt(id.replace("P00-", "")) + 1;
            return String.format("P00-%03d", newPaymentId);
        } else {
            return "P00-001";
        }
    }
}
