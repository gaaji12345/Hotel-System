package bo.impl;

import bo.custom.UserBo;
import dao.custom.UserDao;
import dao.impl.UserDaoImpl;
import dto.Userdto;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBoImpl implements UserBo {

    UserDao userDao=new UserDaoImpl();
    @Override
    public boolean deleteUser(String id) throws SQLException {
        return userDao.delete(id);
    }

    @Override
    public boolean updateUser(Userdto dto) throws SQLException {
        return false;
    }

    @Override
    public boolean addUser(Userdto dto) throws SQLException {
        return userDao.update(new User(dto.getId(), dto.getName(), dto.getPassword(), dto.getTitle()));
    }

    @Override
    public Userdto searchUser(String id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Userdto> getAllUsers() throws SQLException {
        return null;
    }
}
