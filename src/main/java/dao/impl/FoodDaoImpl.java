package dao.impl;

import dao.custom.FoodDao;
import dao.util.SqlUtil;
import entity.Food;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodDaoImpl implements FoodDao {
    @Override
    public boolean delete(String id) throws SQLException {
        return SqlUtil.execute("DELETE FROM food WHERE foodId = ?", id);
    }

    @Override
    public boolean update(Food entity) throws SQLException {
        return SqlUtil.execute("UPDATE food SET foodName = ?, foodDetails = ?, foodPrice = ? WHERE foodId = ?", entity.getName(), entity.getDetails(), entity.getPrice(), entity.getId());
    }

    @Override
    public boolean add(Food entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO food(foodId, foodName, foodDetails, foodPrice) VALUES(?, ?, ?, ?)", entity.getId(), entity.getName(), entity.getDetails(), entity.getPrice());
    }

    @Override
    public Food search(String id) throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM food WHERE foodId = ?", id);
        if(rst.next()) {
            return new Food(rst.getString(1), rst.getString(2), rst.getString(3),  rst.getDouble(4));
        }
        return null;
    }

    @Override
    public ArrayList<Food> getAll() throws SQLException {
        ArrayList<Food> allFoodDetails = new ArrayList<>();
        ResultSet rst = SqlUtil.execute("SELECT * FROM food");
        while (rst.next()) {
            allFoodDetails.add(new Food(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allFoodDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT foodId FROM food ORDER BY foodId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("foodId");
            int newFoodId = Integer.parseInt(id.replace("F00-", "")) + 1;
            return String.format("F00-%03d", newFoodId);
        } else {
            return "F00-001";
        }
    }
}
