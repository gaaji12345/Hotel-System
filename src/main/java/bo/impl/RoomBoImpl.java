package bo.impl;

import bo.custom.RoomBo;
import dao.custom.RoomDao;
import dao.impl.RoomDaiImpl;
import db.DBConnection;
import dto.Roomdto;
import entity.Room;

import java.sql.PreparedStatement;
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
        return roomDao.add(new Room(dto.getId(), dto.getDetails(), dto.getRoomType(), dto.getPrice()));
    }

    @Override
    public Roomdto searchRoom(String id) throws SQLException {
        Room r = roomDao.search(id);
        return new Roomdto(r.getId(), r.getDetails(), r.getRoomType(), r.getPrice());
    }

    @Override
    public ArrayList<Roomdto> getAllRooms() throws SQLException {
        ArrayList<Room> all = roomDao.getAll();
        ArrayList<Roomdto> allRoomDetails = new ArrayList<>();
        for(Room r : all){
            allRoomDetails.add(new Roomdto(r.getId(), r.getDetails(), r.getRoomType(), r.getPrice()));
        }
        return allRoomDetails;
    }

    @Override
    public String generateNewRoomID() throws SQLException, ClassNotFoundException {
        return roomDao.generateNewID();
    }

    @Override
    public void releaseRoom(String roomId, String release) throws SQLException {
        String sql = "UPDATE room SET roomDetails = ? WHERE roomId = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, release);
        pstm.setString(2, roomId);
        pstm.executeUpdate();

    }
}
