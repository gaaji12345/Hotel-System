package dao.custom;

import dao.util.SqlUtil;
import entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDao {

    public boolean delete(String id) throws SQLException ;


    public boolean update(Payment entity) throws SQLException ;



    public boolean add(Payment entity) throws SQLException ;


    public Payment search(String id) throws SQLException ;



    public ArrayList<Payment> getAll() throws SQLException ;



    public String generateNewID() throws SQLException, ClassNotFoundException ;

}
