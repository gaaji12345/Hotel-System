package controller;

import bo.custom.GuesBo;
import bo.impl.GuestBoImpl;
import dao.impl.LOginDaoImpl;
import dto.Guestdto;
import dto.tm.GuestTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class GuestFormController {
    public TextField txtUId;
    public TextField txtGid;
    public TextField txtPasport;
    public TextField txtCountry;
    public TextField txtZip;
    public CheckBox chkMale;
    public CheckBox chkFenale;
    public Button btnSave;
    public Button btnDelte;
    public Button btnUpdate;
    public TableView <GuestTm>tblMain;
    public TableColumn cblUId;
    public TableColumn cblGid;
    public TableColumn cblFname;
    public TableColumn cblGender;
    public TableColumn cblCountry;
    public TableColumn cblZip;
    public TableColumn cblPassport;
    public TextField txtName;

    GuesBo guesBo=new GuestBoImpl();

    public void initialize() {
        cblUId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        cblGid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cblFname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cblGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cblCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        cblZip.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        cblPassport.setCellValueFactory(new PropertyValueFactory<>("passportId"));

        loadAllGuest();
        setSelectToTxt();

    }

    private void loadAllGuest() {
        try {
            ObservableList<GuestTm> obList = FXCollections.observableArrayList();
            List<Guestdto> gusList = guesBo.getAllGuests();

            for (Guestdto guestDTO : gusList) {
                obList.add(new GuestTm(
                        guestDTO.getUserId(),
                        guestDTO.getId(),
                        guestDTO.getName(),
                        guestDTO.getGender(),
                        guestDTO.getCountry(),
                        guestDTO.getZipCode(),
                        guestDTO.getPassportId()
                ));
            }
            tblMain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setSelectToTxt() {

        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtUId.setText(newSelection.getUserId());
                txtGid.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                if (newSelection.getGender().equals("Male")) {
                    chkMale.setSelected(true);
                    chkFenale.setSelected(false);
                } else if (newSelection.getGender().equals("Female")) {
                    chkMale.setSelected(false);
                    chkFenale.setSelected(true);
                } else {
                    chkMale.setSelected(false);
                    chkFenale.setSelected(false);
                }
                txtCountry.setText(newSelection.getCountry());
                txtZip.setText(newSelection.getZipCode());
                txtPasport.setText(newSelection.getPassportId());
            }
        });
    }

    private void initUI() {
        txtPasport.setOnAction(event -> btnSave.fire());
    }

    public void txtSerach_OnAc(ActionEvent actionEvent) {
        try {
            Guestdto guestDTO = (Guestdto) guesBo.searchGuest(txtGid.getText());
            if (guestDTO != null) {
                txtUId.setText(guestDTO.getUserId());
                txtGid.setText(guestDTO.getId());
                txtName.setText(guestDTO.getName());
                String gender = guestDTO.getGender();
                if (gender != null && gender.equals("Male")) {
                    chkMale.setSelected(true);
                } else if (gender != null && gender.equals("Female")) {
                    chkFenale.setSelected(true);
                }
                txtCountry.setText(guestDTO.getCountry());
                txtZip.setText(guestDTO.getZipCode());
                txtPasport.setText(guestDTO.getPassportId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }

    }

    public void save_OnAction(ActionEvent actionEvent) {
        try {
            String userId = txtUId.getText();
            String id = txtGid.getText();
            String name = txtName.getText();
            String gender = "";
            if (chkMale.isSelected()) {
                gender = "Male";
                chkFenale.setSelected(false);
            } else if (chkFenale.isSelected()) {
                gender = "Female";
                chkMale.setSelected(false);
            }
            String country = txtCountry.getText();
            String zipcode = txtZip.getText();
            String passportId = txtPasport.getText();

            if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || country.isEmpty() || zipcode.isEmpty() || passportId.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            // Save guest to database
            boolean isSaved = guesBo.addGuest(new Guestdto(userId, id, name, gender, country, zipcode, passportId));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Guest saved!").show();
                loadAllGuest();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
        }
    }

    public void delet_OnAction(ActionEvent actionEvent) {
        String id = txtGid.getText();
        try {
            boolean isDeleted = guesBo.deleteGuest(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                loadAllGuest();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void update_OnAction(ActionEvent actionEvent) {
        String userId = txtUId.getText();
        String id = txtGid.getText();
        String name = txtName.getText();
        String gender = "";
        if (chkMale.isSelected()) {
            gender = "Male";
            chkFenale.setSelected(false);
        } else if (chkFenale.isSelected()) {
            gender = "Female";
            chkMale.setSelected(false);
        }
        String country = txtCountry.getText();
        String zipcode = txtZip.getText();
        String passportId = txtPasport.getText();

        if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || country.isEmpty() || zipcode.isEmpty() || passportId.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }

        try {
            boolean isUpdated = guesBo.updateGuest(new Guestdto(userId, id, name, gender, country, zipcode, passportId));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Guest updated!").show();
                loadAllGuest();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "Guest Not updated!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }
}
