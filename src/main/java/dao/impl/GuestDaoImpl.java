package dao.impl;

import dao.custom.GuestDao;
import dao.util.SqlUtil;
import entity.Guest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuestDaoImpl implements GuestDao {

    @Override
    public boolean delete(String id) throws SQLException {
        return SqlUtil.execute("DELETE FROM guest WHERE guestId = ?", id);
    }

    @Override
    public boolean update(Guest entity) throws SQLException {
        return SqlUtil.execute("UPDATE guest SET userId = ?, guestName = ?, gender = ?, guestCountry = ?, guestZipcode = ?, guestPassportId = ? WHERE guestId = ?", entity.getUserId(), entity.getGuestName(), entity.getGender(), entity.getGuestCountry(), entity.getGuestZipcode(), entity.getGuestPassportId(), entity.getGuestId());
    }

    @Override
    public boolean add(Guest entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO guest(userId, guestId, guestName, gender, guestCountry, guestZipcode, guestPassportId) VALUES(?, ?, ?, ?, ?, ?, ?)", entity.getUserId(), entity.getGuestId(), entity.getGuestName(), entity.getGender(), entity.getGuestCountry(), entity.getGuestZipcode(), entity.getGuestPassportId());
    }

    @Override
    public Guest search(String id) throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM guest WHERE guestId = ?", id);
        if (rst.next()) {
            return new Guest(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;
    }

    @Override
    public ArrayList<Guest> getAll() throws SQLException {
        ArrayList<Guest> allGuestDetails = new ArrayList<>();
        ResultSet rst = SqlUtil.execute("SELECT * FROM guest");
        while (rst.next()) {
            allGuestDetails.add(new Guest(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7)));
        }
        return allGuestDetails;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT guestId FROM guest ORDER BY guestId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("guestId");
            int newGuestId = Integer.parseInt(id.replace("G00-", "")) + 1;
            return String.format("G00-%03d", newGuestId);
        } else {
            return "G00-001";
        }
    }
}
