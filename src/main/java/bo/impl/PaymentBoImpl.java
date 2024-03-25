package bo.impl;

import bo.custom.PaymentBo;
import dao.custom.PaymentDao;
import dao.impl.PaymentDaoImpl;
import dao.util.SqlUtil;
import db.DBConnection;
import dto.PaymentDto;
import entity.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBoImpl  implements PaymentBo{
    PaymentDao paymentDAO=new PaymentDaoImpl();
    @Override
    public boolean deletePayment(String id) throws SQLException {
        return paymentDAO.delete(id);
    }

    @Override
    public boolean updatePayment(PaymentDto dto) throws SQLException {
        return paymentDAO.update(new Payment(dto.getPaymentId(), dto.getGuestId(), dto.getGuestName(), dto.getResId(), dto.getRoomId(), dto.getCheckIn(), dto.getCheckOut(), dto.getOrderAm(), dto.getTotal()));
    }

    @Override
    public boolean addPayment(PaymentDto dto) throws SQLException {
        return paymentDAO.add(new Payment(dto.getPaymentId(), dto.getGuestId(), dto.getGuestName(), dto.getResId(), dto.getRoomId(), dto.getCheckIn(), dto.getCheckOut(), dto.getOrderAm(), dto.getTotal()));
    }

    @Override
    public ArrayList<PaymentDto> getAllPayments() throws SQLException {
        ArrayList<Payment> all = paymentDAO.getAll();
        ArrayList<PaymentDto> allPaymentDetails = new ArrayList<>();
        for(Payment p : all) {
            allPaymentDetails.add(new PaymentDto(p.getPaymentId(), p.getGuestId(), p.getGuestName(), p.getBookingId(), p.getRoomId(), p.getCheckInDate(), p.getCheckOutDate(), p.getOrdersAmount(), p.getTotalPrice()));
        }
        return allPaymentDetails;
    }

    @Override
    public String generateNewPaymentID() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNewID();
    }

    @Override
    public Double getOrderAmount(String id) throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT orderId FROM foodOrders WHERE bookingId = ?", id);
        ArrayList<String> orderIds = new ArrayList<>();
        while (rst.next()) {
            String orderId = rst.getString("orderId");
            orderIds.add(orderId);
        }

        double totalAmount = 0.0;
        String sql2 = "SELECT amount FROM foodOrderDetail WHERE orderId = ?";
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql2);
        for (String orderId : orderIds) {
            pst.setString(1, orderId);
            ResultSet rs = pst.executeQuery();
            double orderAmount = 0.0;
            while (rs.next()) {
                orderAmount += rs.getDouble("amount");
            }

            totalAmount += orderAmount;
        }
        return totalAmount;
    }
}
