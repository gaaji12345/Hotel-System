package bo.custom;

import dao.util.SqlUtil;
import db.DBConnection;
import dto.PaymentDto;
import entity.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBo {

    public boolean deletePayment(String id) throws SQLException ;


    public boolean updatePayment(PaymentDto dto) throws SQLException ;



    public boolean addPayment(PaymentDto dto) throws SQLException ;



    public ArrayList<PaymentDto> getAllPayments() throws SQLException ;


    public String generateNewPaymentID() throws SQLException, ClassNotFoundException ;



    public Double getOrderAmount(String id) throws SQLException ;

}
