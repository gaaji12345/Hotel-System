package controller;

import bo.custom.LoginBo;
import bo.impl.LoginBoImpl;
import dto.Logindto;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginFormController {
    public ImageView root;
    public TextField txtUserName;
    public TextField txtpassword;
    public ComboBox cmbPostions;
    public PasswordField txtPassFld;
    public Button btnLogin;
    public CheckBox chkBox;

    LoginBo loginBo=new LoginBoImpl();
    public void initialize() {
        loadTitles();
        //initUI();
    }

    private void loadTitles() {
        try {
            ArrayList<Logindto> allLoginDetails = loginBo.getAllLogins();
            for (Logindto c : allLoginDetails) {
                cmbPostions.getItems().add(c.getTitle());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Titles").show();
        }
    }

    public void pw_OnAction(MouseEvent mouseEvent) {
    }

    public void chk_boxOnAction(MouseEvent mouseEvent) {
    }


    public void login_OnAction(ActionEvent actionEvent) {
    }
}
