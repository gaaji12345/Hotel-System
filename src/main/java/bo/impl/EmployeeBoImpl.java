package bo.impl;

import bo.custom.EmployeeBo;
import dao.custom.EmployeeDao;
import dao.impl.EmployeeDaoImpl;
import dto.Employeedto;
import entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBoImpl implements EmployeeBo {
    EmployeeDao employeeDAO=new EmployeeDaoImpl();
    @Override
    public boolean deleteEmployee(String id) throws SQLException {
        return employeeDAO.delete(id);
    }

    @Override
    public boolean updateEmployee(Employeedto dto) throws SQLException {
        return employeeDAO.update(new Employee(dto.getName(), dto.getGender(), dto.getEmail(), dto.getNic(), dto.getAddress(), dto.getId()));
    }

    @Override
    public boolean addEmployee(Employeedto dto) throws SQLException {
        return employeeDAO.add(new Employee( dto.getUserId(), dto.getId(), dto.getName(), dto.getGender(), dto.getEmail(), dto.getNic(), dto.getAddress()));
    }

    @Override
    public Employeedto searchEmployee(String id) throws SQLException {
        Employee e = employeeDAO.search(id);
        return new Employeedto(e.getEmpId(), e.getEmpName(), e.getGender(), e.getAddress(), e.getEmail(), e.getNic());
    }

    @Override
    public ArrayList<Employeedto> getAllEmployees() throws SQLException {
        ArrayList<Employee> all = employeeDAO.getAll();
        ArrayList<Employeedto> allEmployeeDetails = new ArrayList<>();
        for(Employee b: all){
            allEmployeeDetails.add(new Employeedto(b.getUserId(),b.getEmpId(), b.getEmpName(), b.getGender(), b.getEmail(), b.getNic(),b.getAddress()));
        }
        return allEmployeeDetails;
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }

}
