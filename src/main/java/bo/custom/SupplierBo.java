package bo.custom;

import dto.Supplierdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBo {

    public boolean deleteSupplier(String id) throws SQLException;


    public boolean updateSupplier(Supplierdto dto) throws SQLException ;


    public boolean addSupplier(Supplierdto dto) throws SQLException ;


    public Supplierdto searchSupplier(String id) throws SQLException ;


    public ArrayList<Supplierdto> getAllSuppliers() throws SQLException ;


    public String generateNewSupplierID() throws SQLException, ClassNotFoundException ;
}
