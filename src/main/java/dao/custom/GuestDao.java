package dao.custom;

import entity.Guest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GuestDao {

    public boolean delete(String id) throws SQLException ;



    public boolean update(Guest entity) throws SQLException ;



    public boolean add(Guest entity) throws SQLException ;



    public Guest search(String id) throws SQLException ;



    public ArrayList<Guest> getAll() throws SQLException ;



    public String generateNewID() throws SQLException, ClassNotFoundException ;


}
