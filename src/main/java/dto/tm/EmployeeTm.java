package dto.tm;

public class EmployeeTm implements Comparable<EmployeeTm>{
    private String userId;
    private String id;
    private String name;
    private String gender;
    private String email;
    private String nic;
    private String address;

    @Override
    public int compareTo(EmployeeTm o) {
        return id.compareTo(o.id);
    }
}
