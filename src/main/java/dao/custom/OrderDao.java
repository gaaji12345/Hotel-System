package dao.custom;

import entity.FoodOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDao {

    public boolean delete(String s) throws SQLException ;


    public boolean update(FoodOrder entity) throws SQLException ;



    public boolean add(FoodOrder entity) throws SQLException ;


    public FoodOrder search(String s) throws SQLException ;



    public ArrayList<FoodOrder> getAll() throws SQLException ;



    public String generateNewID() throws SQLException, ClassNotFoundException ;

}
