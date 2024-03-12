package controller;

import bo.custom.UserBo;
import bo.impl.UserBoImpl;
import dto.Userdto;
import dto.tm.UserTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

public class UserController {

    public AnchorPane root;
    public TextField txtId;
    public TextField txtName;
    public TextField txtPassword;
    public TextField txtPosstion;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView <UserTm>tblMian;
    public TableColumn cblid;
    public TableColumn cblName;
    public TableColumn cblPassword;
    public TableColumn cblpos;

    UserBo userBo=new UserBoImpl();

    public void initialize() {
        cblid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cblName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cblPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        cblpos.setCellValueFactory(new PropertyValueFactory<>("title"));
   
        loadallusers();
      
    }

    private void loadallusers() {
        try {
            ObservableList<UserTm> obList = FXCollections.observableArrayList();
            List<Userdto> userDTOList = userBo.getAllUsers();

            for (Userdto userDTO : userDTOList) {
                obList.add(new UserTm(
                        userDTO.getId(),
                        userDTO.getName(),
                        userDTO.getPassword(),
                        userDTO.getTitle()
                ));
            }
            tblMian.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void saveON(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String password =txtPassword.getText();
            String title = txtPosstion.getText();



            boolean isSaved = userBo.addUser(new Userdto(id, name, password, title));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                loadallusers();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void updateON(ActionEvent actionEvent) {
    }

    public void deleteOn(ActionEvent actionEvent) {
    }
}
