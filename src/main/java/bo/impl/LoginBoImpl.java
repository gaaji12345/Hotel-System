package bo.impl;

import bo.custom.LoginBo;
import dao.custom.LoginDao;
import dao.impl.LOginDaoImpl;
import dto.Logindto;
import entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginBoImpl implements LoginBo {
    LoginDao loginDao=new LOginDaoImpl();
    @Override
    public ArrayList<Logindto> getAllLogins() throws SQLException {
        ArrayList<Login> all = loginDao.getAll();
        ArrayList<Logindto> allLoginDetails = new ArrayList<>();
        for(Login l : all){
            allLoginDetails.add(new Logindto(l.getUserName(), l.getPassWord(), l.getTitle()));
        }
        return allLoginDetails;
    }
}
