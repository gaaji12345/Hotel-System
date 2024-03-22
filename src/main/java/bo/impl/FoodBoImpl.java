package bo.impl;

import bo.custom.FoodBo;
import dao.custom.FoodDao;
import dao.impl.FoodDaoImpl;
import dto.Fooddto;
import entity.Food;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodBoImpl implements FoodBo {
    FoodDao foodDAO=new FoodDaoImpl();
    @Override
    public boolean deleteFood(String id) throws SQLException {
        return foodDAO.delete(id);
    }

    @Override
    public boolean updateFood(Fooddto dto) throws SQLException {
        return foodDAO.update(new Food(dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice()));
    }

    @Override
    public boolean addFood(Fooddto dto) throws SQLException {
        return foodDAO.add(new Food(dto.getId(), dto.getName(), dto.getDetails(), dto.getPrice()));
    }

    @Override
    public Fooddto searchFood(String id) throws SQLException {
        Food f = foodDAO.search(id);
        return new Fooddto(f.getId(), f.getName(), f.getDetails(), f.getPrice());
    }

    @Override
    public ArrayList<Fooddto> getAllFoods() throws SQLException {
        ArrayList<Food> all = foodDAO.getAll();
        ArrayList<Fooddto> allFoodDetails = new ArrayList<>();
        for(Food f : all){
            allFoodDetails.add(new Fooddto(f.getId(), f.getName(), f.getDetails(), f.getPrice()));
        }
        return allFoodDetails;
    }

    @Override
    public String generateNewFoodID() throws SQLException, ClassNotFoundException {
        return foodDAO.generateNewID();
    }
}
