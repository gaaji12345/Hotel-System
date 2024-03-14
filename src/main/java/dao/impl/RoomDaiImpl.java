package dao.impl;

import dao.custom.RoomDao;
import dao.util.SqlUtil;
import entity.Room;

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
        return null;
    }

    @Override
    public ArrayList<Room> getAll() throws SQLException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
