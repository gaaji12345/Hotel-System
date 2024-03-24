package dao.custom;

import entity.FoodOrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDao {

    public boolean delete(String s) throws SQLException ;



    public boolean update(FoodOrderDetails entity) throws SQLException ;



    public boolean add(FoodOrderDetails entity) throws SQLException ;



    public FoodOrderDetails search(String s) throws SQLException ;



    public ArrayList<FoodOrderDetails> getAll() throws SQLException ;



    public String generateNewID() throws SQLException, ClassNotFoundException ;

}
