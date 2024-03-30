package bo.impl;

import bo.custom.SupplierBo;
import dao.custom.SupplierDao;
import dao.impl.SupplierdaoImpl;
import dto.Supplierdto;
import entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBoImpl  implements SupplierBo {
    SupplierDao supplierDAO=new SupplierdaoImpl();
    @Override
    public boolean deleteSupplier(String id) throws SQLException {
        return supplierDAO.delete(id);
    }

    @Override
    public boolean updateSupplier(Supplierdto dto) throws SQLException {
        return supplierDAO.update(new Supplier(dto.getName(), dto.getContact(), dto.getDetails(), dto.getId()));
    }

    @Override
    public boolean addSupplier(Supplierdto dto) throws SQLException {
        return supplierDAO.add(new Supplier(dto.getId(), dto.getName(), dto.getContact(), dto.getDetails()));
    }

    @Override
    public Supplierdto searchSupplier(String id) throws SQLException {
        Supplier s = supplierDAO.search(id);
        return new Supplierdto(s.getSupId(), s.getSupName(), s.getSupContact(), s.getSuppyDetail());
    }

    @Override
    public ArrayList<Supplierdto> getAllSuppliers() throws SQLException {
        ArrayList<Supplier> all = supplierDAO.getAll();
        ArrayList<Supplierdto> allSupplierDetails = new ArrayList<>();
        for(Supplier s : all){
            allSupplierDetails.add(new Supplierdto(s.getSupId(), s.getSupName(), s.getSupContact(), s.getSuppyDetail()));
        }
        return allSupplierDetails;
    }

    @Override
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewID();
    }
}
