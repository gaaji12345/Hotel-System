package dao.custom;

import entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDao {
  
    public boolean delete(String id) throws SQLException ;


    public boolean update(Employee entity) throws SQLException ;


    public boolean add(Employee entity) throws SQLException ;


    public Employee search(String id) throws SQLException ;


    public ArrayList<Employee> getAll() throws SQLException ;


    public String generateNewID() throws SQLException, ClassNotFoundException ;


}
