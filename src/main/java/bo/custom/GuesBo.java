package bo.custom;

import dto.Guestdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GuesBo {



    public boolean deleteGuest(String id) throws SQLException ;


    public boolean updateGuest(Guestdto dto) throws SQLException ;



    public boolean addGuest(Guestdto dto) throws SQLException ;



    public Guestdto searchGuest(String id) throws SQLException ;



    public ArrayList<Guestdto> getAllGuests() throws SQLException ;



    public String generateNewGuestID() throws SQLException, ClassNotFoundException ;


}
