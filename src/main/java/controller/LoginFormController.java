package controller;

import bo.custom.LoginBo;
import bo.impl.LoginBoImpl;
import db.DBConnection;
import dto.Logindto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginFormController {
    public ImageView root;
    public TextField txtUserName;
  //  public TextField txtpassword;
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
        String title = (String) cmbPostions.getValue();
        String userName = txtUserName.getText();
        String password = txtPassFld.getText();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM login WHERE title = ? AND userName = ? AND password = ?");
            stm.setString(1, title);
            stm.setString(2, userName);
            stm.setString(3, password);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                // Login successful, load appropriate form
                String path = "/view/";
                switch(title) {
                    case "Manager":
                        path += "DashBoard.fxml";
                        break;
                    case "Receptionist":
                        path += "receptionist_form.fxml";
                        break;
                    // add more cases as needed for other user types
                }
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(path))));
                stage.setTitle("Manage");
                stage.centerOnScreen();
                stage.show();

            } else {
                // Login failed, show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password!");
                alert.showAndWait();
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while trying to login!");
            alert.showAndWait();
        }
    }
    }

