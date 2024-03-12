package dao.custom;

import entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginDao {
    public ArrayList<Login> getAll() throws SQLException;
}
