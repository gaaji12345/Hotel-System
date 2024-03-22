package dao.custom;

import entity.Food;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface FoodDao {


    public boolean delete(String id) throws SQLException ;



    public boolean update(Food entity) throws SQLException ;


    public boolean add(Food entity) throws SQLException ;



    public Food search(String id) throws SQLException ;



    public ArrayList<Food> getAll() throws SQLException ;



    public String generateNewID() throws SQLException, ClassNotFoundException ;

}
