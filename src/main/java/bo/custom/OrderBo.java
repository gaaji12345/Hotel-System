package bo.custom;

import db.DBConnection;
import dto.OrderDetailsdto;
import dto.Orderdto;
import entity.FoodOrder;
import entity.FoodOrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBo {

    public boolean saveOrder(Orderdto dto) ;

    public ArrayList<Orderdto> getAllFoodOrders() throws SQLException ;


    public String generateNewOrderID() throws SQLException, ClassNotFoundException ;


    public ArrayList<OrderDetailsdto> getAllFoodOrderDetails() throws SQLException ;
}
