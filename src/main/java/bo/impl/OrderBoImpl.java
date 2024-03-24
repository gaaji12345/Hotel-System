package bo.impl;

import bo.custom.OrderBo;
import dao.custom.OrderDao;
import dao.custom.OrderDetailDao;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderDetailDaoImpl;
import db.DBConnection;
import dto.OrderDetailsdto;
import dto.Orderdto;
import entity.FoodOrder;
import entity.FoodOrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBoImpl implements OrderBo {
    OrderDetailDao orderDetailsDAO=new OrderDetailDaoImpl();
    OrderDao orderDao=new OrderDaoImpl();
    @Override
    public boolean saveOrder(Orderdto dto) {
        /*Transaction*/
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            FoodOrder orderEntity = new FoodOrder(dto.getOrderID(), dto.getDate(), dto.getBookingID());


            for (OrderDetailsdto odDTO : dto.getOrderDetails()) {
                FoodOrderDetails orderDetailsEntity = new FoodOrderDetails(odDTO.getOrderID(), odDTO.getMealID(), odDTO.getQty(), odDTO.getTotal(), odDTO.getDate());

                if (!orderDao.add(orderEntity) || !orderDetailsDAO.add(orderDetailsEntity)) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
//                //Search & Update Item
//                ItemDTO item = findItemByID(orderDetailsEntity.getItemCode());
//                item.setQtyOnHand(item.getQtyOnHand() - orderDetailsEntity.getQty());
//                boolean itemUpdate = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()));
//
//                if (!itemUpdate) {
//                    connection.rollback();
//                    connection.setAutoCommit(true);
//                    return false;
//                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Orderdto> getAllFoodOrders() throws SQLException {
        ArrayList<FoodOrder> all = orderDao.getAll();
        ArrayList<Orderdto> allOrders = new ArrayList<>();
        for(FoodOrder f : all){
            allOrders.add(new Orderdto(f.getOrderId(), f.getDate(), f.getBookingId()));
        }
        return allOrders;
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDao.generateNewID();
    }

    @Override
    public ArrayList<OrderDetailsdto> getAllFoodOrderDetails() throws SQLException {
        ArrayList<FoodOrderDetails> all = orderDetailsDAO.getAll();
        ArrayList<OrderDetailsdto> allOrderDetails = new ArrayList<>();
        for(FoodOrderDetails f : all){
            allOrderDetails.add(new OrderDetailsdto(f.getOrderID(), f.getFoodID(), f.getQty(), f.getAmount(), f.getDate()));
        }
        return allOrderDetails;
    }
}
