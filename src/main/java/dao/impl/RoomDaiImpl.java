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
        return false;
    }

    @Override
    public boolean add(Room entity) throws SQLException {
        return false;
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
