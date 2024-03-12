package bo.custom;

import dto.Logindto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginBo {
    public ArrayList<Logindto> getAllLogins() throws SQLException;
}
