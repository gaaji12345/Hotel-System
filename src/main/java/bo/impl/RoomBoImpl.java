package bo.impl;

import bo.custom.RoomBo;
import dao.custom.RoomDao;
import dao.impl.RoomDaiImpl;
import dto.Roomdto;
import entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoomBoImpl  implements RoomBo {
    RoomDao roomDao=new RoomDaiImpl();


    @Override
    public boolean deleteRoom(String id) throws SQLException {
        return roomDao.delete(id);
    }

    @Override
    public boolean updateRoom(Roomdto dto) throws SQLException {
        return roomDao.update(new Room(dto.getId(), dto.getDetails(), dto.getRoomType(), dto.getPrice()));
    }

    @Override
    public boolean addRoom(Roomdto dto) throws SQLException {
        return false;
    }

    @Override
    public Roomdto searchRoom(String id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Roomdto> getAllRooms() throws SQLException {
        return null;
    }

    @Override
    public String generateNewRoomID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public void releaseRoom(String roomId, String release) throws SQLException {

    }
}
