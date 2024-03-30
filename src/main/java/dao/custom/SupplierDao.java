package dao.custom;

import entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDao {

    public boolean delete(String id) throws SQLException ;


    public boolean update(Supplier entity) throws SQLException;

    public boolean add(Supplier entity) throws SQLException ;


    public Supplier search(String id) throws SQLException ;



    public ArrayList<Supplier> getAll() throws SQLException ;



    public String generateNewID() throws SQLException, ClassNotFoundException ;

}
