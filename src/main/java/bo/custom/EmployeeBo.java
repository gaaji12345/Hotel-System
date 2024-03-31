package bo.custom;

import dto.Employeedto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBo {

    public boolean deleteEmployee(String id) throws SQLException ;


    public boolean updateEmployee(Employeedto dto) throws SQLException ;


    public boolean addEmployee(Employeedto dto) throws SQLException ;


    public Employeedto searchEmployee(String id) throws SQLException ;


    public ArrayList<Employeedto> getAllEmployees() throws SQLException ;


    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException ;

}
