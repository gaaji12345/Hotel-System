package dao.impl;

import dao.custom.SupplierDao;
import dao.util.SqlUtil;
import entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierdaoImpl implements SupplierDao {
    @Override
    public boolean delete(String id) throws SQLException {
        return SqlUtil.execute("DELETE FROM supplier WHERE supId = ?", id);
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return SqlUtil.execute("UPDATE supplier SET supName = ?, supContact = ?, supplyDetail = ? WHERE supId = ?", entity.getSupName(), entity.getSupContact(), entity.getSuppyDetail(), entity.getSupId());
    }

    @Override
    public boolean add(Supplier entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO supplier(supId, supName, supContact, supplyDetail) VALUES(?, ?, ?, ?)", entity.getSupId(), entity.getSupName(), entity.getSupContact(), entity.getSuppyDetail());
    }

    @Override
    public Supplier search(String id) throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM supplier WHERE supId = ?", id);
        if(rst.next()) {
            return new Supplier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
        }
        return null;    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ArrayList<Supplier> allSupplierDetails = new ArrayList<>();
        ResultSet rst = SqlUtil.execute("SELECT * FROM supplier");
        while (rst.next()) {
            allSupplierDetails.add(new Supplier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return allSupplierDetails;    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SqlUtil.execute("SELECT supId FROM supplier ORDER BY supId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("supId");
            int newSupId = Integer.parseInt(id.replace("SP0-", "")) + 1;
            return String.format("SP0-%03d", newSupId);
        } else {
            return "SP0-001";
        }
    }
}
