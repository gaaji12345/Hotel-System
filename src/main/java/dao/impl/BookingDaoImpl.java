package dao.impl;

import dao.custom.BookingDao;
import dao.util.SqlUtil;
import entity.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingDaoImpl implements BookingDao {
    @Override
    public boolean delete(String id) throws SQLException {
        return SqlUtil.execute("DELETE FROM booking WHERE bookingId = ?",id);
    }

    @Override
    public boolean update(Booking entity) throws SQLException {
        return SqlUtil.execute("UPDATE booking SET guestId = ?, roomId = ?, checkOut = ? WHERE bookingId = ?", entity.getGuestId(), entity.getRoomId(), entity.getCheckOut(), entity.getBookingId());
    }

    @Override
    public boolean add(Booking entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO booking(guestId, bookingId, bookingDate, roomId, checkIn, checkOut)VALUES(?, ?, ?, ?, ?, ?)",entity.getGuestId(),entity.getBookingId(),entity.getBookingDate(),entity.getRoomId(),entity.getCheckIn(),entity.getCheckOut());
    }

    @Override
    public Booking search(String id) throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM booking WHERE bookingId = ?",id);
        if(rst.next()){
            return new Booking(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6));
        }
        return null;
    }

    @Override
    public ArrayList<Booking> getAll() throws SQLException {
        ArrayList<Booking> allBookings= new ArrayList<>();
        ResultSet rst = SqlUtil.execute("SELECT * FROM booking");
        while (rst.next()){
            allBookings.add(new Booking(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6)));
        }
        return allBookings;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT bookingId FROM booking ORDER BY bookingId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("bookingId");
            int newBookingId = Integer.parseInt(id.replace("B00-", "")) + 1;
            return String.format("B00-%03d", newBookingId);
        } else {
            return "B00-001";
        }
    }
}
