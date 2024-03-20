package bo.impl;

import bo.custom.BookingBo;
import dao.custom.BookingDao;
import dao.impl.BookingDaoImpl;
import db.DBConnection;
import dto.Bookingdto;
import entity.Booking;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingBoImpl implements BookingBo {

    BookingDao bookingDAO=new BookingDaoImpl();
    @Override
    public boolean deleteBooking(String id) throws SQLException {
        return bookingDAO.delete(id);
    }

    @Override
    public boolean updateBooking(Bookingdto dto) throws SQLException {
        return bookingDAO.update(new Booking(dto.getGuestId(), dto.getRoomId(), dto.getCheckOut(), dto.getBookingId()));
    }

    @Override
    public boolean addBooking(Bookingdto dto) throws SQLException {
        return bookingDAO.add(new Booking(dto.getGuestId(),dto.getBookingId(),dto.getBookingDate(),dto.getRoomId(),dto.getCheckIn(),dto.getCheckOut()));
    }

    @Override
    public Bookingdto searchBooking(String id) throws SQLException {
        Booking b=bookingDAO.search(id);
        return new Bookingdto(b.getGuestId(), b.getBookingId(), b.getBookingDate(), b.getRoomId(), b.getCheckIn(), b.getCheckOut());
    }

    @Override
    public ArrayList<Bookingdto> getAllBookings() throws SQLException {
        ArrayList<Booking> all = bookingDAO.getAll();
        ArrayList<Bookingdto> allBookings= new ArrayList<>();
        for (Booking c:all){
            allBookings.add(new Bookingdto(c.getGuestId(), c.getBookingId(), c.getBookingDate(), c.getRoomId(), c.getCheckIn(), c.getCheckOut()));
        }
        return allBookings;
    }

    @Override
    public String generateNewBookingID() throws SQLException, ClassNotFoundException {
        return bookingDAO.generateNewID();
    }

    @Override
    public void releaseRoom(String roomId) throws SQLException {
        String release = "Booked";
        String sql = "UPDATE room SET roomDetails = ? WHERE roomId = ?";

        PreparedStatement st = DBConnection.getInstance().getConnection().prepareStatement(sql);
        st.setString(1, release);
        st.setString(2, roomId);
        st.executeUpdate();
    }
}
