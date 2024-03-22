package bo.custom;

import dto.Fooddto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FoodBo {

    public boolean deleteFood(String id) throws SQLException ;



    public boolean updateFood(Fooddto dto) throws SQLException ;



    public boolean addFood(Fooddto dto) throws SQLException ;



    public Fooddto searchFood(String id) throws SQLException ;



    public ArrayList<Fooddto> getAllFoods() throws SQLException ;



    public String generateNewFoodID() throws SQLException, ClassNotFoundException ;

}
