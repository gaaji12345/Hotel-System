package dao.custom;

import entity.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingDao {

    public boolean delete(String id) throws SQLException ;



    public boolean update(Booking entity) throws SQLException ;



    public boolean add(Booking entity) throws SQLException ;



    public Booking search(String id) throws SQLException ;



    public ArrayList<Booking> getAll() throws SQLException ;



    public String generateNewID() throws SQLException, ClassNotFoundException ;

}
