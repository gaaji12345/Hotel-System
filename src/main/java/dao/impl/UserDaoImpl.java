package dao.impl;

import dao.custom.UserDao;
import dao.util.SqlUtil;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean delete(String id) throws SQLException {
        return SqlUtil.execute("DELETE FROM user WHERE userId = ?", id);
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return SqlUtil.execute("UPDATE user SET userName = ?, password = ?, title = ? WHERE userId = ?", entity.getUserName(), entity.getPassword(), entity.getTitle(), entity.getUserId());
    }

    @Override
    public boolean add(User entity) throws SQLException {
        return SqlUtil.execute("INSERT INTO User(userId, userName, password, title) VALUES(?, ?, ?, ?)", entity.getUserId(), entity.getUserName(), entity.getPassword(), entity.getTitle());
    }

    @Override
    public User search(String id) throws SQLException {
        ResultSet rst = SqlUtil.execute("SELECT * FROM user WHERE userId = ?", id);
        if(rst.next()) {
            return new User(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
        }
        return null;
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
