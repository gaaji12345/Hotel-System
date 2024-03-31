package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private String userId;
    private String empId;
    private String empName;
    private String gender;
    private String email;
    private String nic;
    private String address;

    public Employee(String name, String gender, String email, String nic, String address, String id) {
        this.empName = name;
        this.empId = id;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.nic = nic;
    }
}
