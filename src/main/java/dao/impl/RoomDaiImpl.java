package dao.impl;

import dao.custom.RoomDao;
import dao.util.SqlUtil;
import entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDaiImpl implements RoomDao {

    @Override
    public boolean delete(String id) throws SQLException {
        return SqlUtil.execute("DELETE FROM room WHERE roomId = ?", id);
    }

    @Override
    public boolean update(Room entity) throws SQLException {
        return SqlUtil.execute("UPDATE Room SET roomDetails = ?, roomType = ?, roomPrice = ? WHERE roomId = ?", entity.getDetails(), entity.getRoomType(), entity.getPrice(), entity.getId());
    }

    @Override
    public boolean add(Room entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO room(roomId, roomDetails, roomType, roomPrice) VALUES(?, ?, ?, ?)", entity.getId(), entity.getDetails(), entity.getRoomType(), entity.getPrice());
    }

    @Override
    public Room search(String id) throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM room WHERE roomId = ?", id);
        if(rst.next()) {
            return new Room(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
        }
        return null;
    }

    @Override
    public ArrayList<Room> getAll() throws SQLException {
        ArrayList<Room> allRoomDetails = new ArrayList<>();
        ResultSet rst = SqlUtil.execute("SELECT * FROM room");
        while (rst.next()) {
            allRoomDetails.add(new Room(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
        }
        return allRoomDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT roomId FROM room ORDER BY roomId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("roomId");
            int newRoomId = Integer.parseInt(id.replace("R00-", "")) + 1;
            return String.format("R00-%03d", newRoomId);
        } else {
            return "R00-001";
        }
    }
}
