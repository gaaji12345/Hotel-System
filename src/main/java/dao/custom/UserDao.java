package dao.custom;

import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDao {

    public boolean delete(String id) throws SQLException ;


    public boolean update(User entity) throws SQLException ;


    public boolean add(User entity) throws SQLException ;


    public User search(String id) throws SQLException;


    public ArrayList<User> getAll() throws SQLException ;


    public String generateNewID() throws SQLException, ClassNotFoundException;
}
