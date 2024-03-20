package bo.custom;

import dto.Bookingdto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingBo {

    public boolean deleteBooking(String id) throws SQLException ;


    public boolean updateBooking(Bookingdto dto) throws SQLException ;



    public boolean addBooking(Bookingdto dto) throws SQLException ;



    public Bookingdto searchBooking(String id) throws SQLException ;



    public ArrayList<Bookingdto> getAllBookings() throws SQLException ;



    public String generateNewBookingID() throws SQLException, ClassNotFoundException ;



    public void releaseRoom(String roomId) throws SQLException ;

}
