package bo.impl;

import bo.custom.GuesBo;
import dao.custom.GuestDao;
import dao.impl.GuestDaoImpl;
import dto.Guestdto;
import entity.Guest;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuestBoImpl  implements GuesBo {
    GuestDao guestDAO = new GuestDaoImpl();
    @Override
    public boolean deleteGuest(String id) throws SQLException {
        return guestDAO.delete(id);
    }

    @Override
    public boolean updateGuest(Guestdto dto) throws SQLException {
        return guestDAO.update(new Guest(dto.getUserId(), dto.getId(), dto.getGender(), dto.getName(), dto.getCountry(), dto.getZipCode(), dto.getPassportId()));
    }

    @Override
    public boolean addGuest(Guestdto dto) throws SQLException {
        return guestDAO.add(new Guest(dto.getUserId(), dto.getId(), dto.getName(), dto.getGender(), dto.getCountry(), dto.getZipCode(), dto.getPassportId()));
    }

    @Override
    public Guestdto searchGuest(String id) throws SQLException {
        Guest g = guestDAO.search(id);
        return new Guestdto(g.getUserId(), g.getGuestId(), g.getGuestName(), g.getGender(), g.getGuestCountry(), g.getGuestZipcode(), g.getGuestPassportId());
    }

    @Override
    public ArrayList<Guestdto> getAllGuests() throws SQLException {
        ArrayList<Guest> all = guestDAO.getAll();
        ArrayList<Guestdto> allGuestDetails = new ArrayList<>();
        for(Guest g : all){
            allGuestDetails.add(new Guestdto(g.getUserId(), g.getGuestId(), g.getGuestName(), g.getGender(), g.getGuestCountry(), g.getGuestZipcode(), g.getGuestPassportId()));
        }
        return allGuestDetails;
    }

    @Override
    public String generateNewGuestID() throws SQLException, ClassNotFoundException {
        return guestDAO.generateNewID();
    }
}
