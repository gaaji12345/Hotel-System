package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employeedto {
    private String userId;
    private String id;
    private String name;
    private String gender;
    private String email;
    private String nic;
    private String address;

    public Employeedto(String id, String name, String gender, String address, String email, String nic) {
    }
}
