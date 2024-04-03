package controller;

import bo.custom.EmployeeBo;
import bo.impl.EmployeeBoImpl;
import dto.Employeedto;
import dto.tm.EmployeeTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public TextField txtUser;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtEmail;
    public CheckBox chkMale;
    public CheckBox chkFemale;
    public TableView <EmployeeTm> tblMian;
    public TableColumn cblUser;
    public TableColumn cblId;
    public TableColumn cblName;
    public TableColumn cblGender;
    public TableColumn cblAddress;
    public TableColumn cblEmail;
    public TableColumn cblNic;
    
    public Button btnSave;
    
    public Button btnUpdate;
    
    public Button btnDelete;
    public TextField txtNic;

    EmployeeBo employeeBo=new EmployeeBoImpl();

    public void initialize() {
        cblUser.setCellValueFactory(new PropertyValueFactory<>("userId"));
        cblUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        cblName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cblGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cblNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        cblAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        //setCellValueFactory();
        getAllEmployee();
        setSelectToTxt();
        //initUI();
    }

    private void setSelectToTxt() {
        tblMian.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtUser.setText(newSelection.getUserId());
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                if (newSelection.getGender().equals("Male")) {
                    chkMale.setSelected(true);
                    chkFemale.setSelected(false);
                } else if (newSelection.getGender().equals("Female")) {
                    chkMale.setSelected(false);
                    chkFemale.setSelected(true);
                } else {
                    chkMale.setSelected(false);
                    chkFemale.setSelected(false);
                }
                txtEmail.setText(newSelection.getEmail());
                txtNic.setText(newSelection.getNic());
                txtAddress.setText(newSelection.getAddress());
            }
        });
    }

    private void getAllEmployee() {
        try {
            ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
            List<Employeedto> employeeDTOList = employeeBo.getAllEmployees();

            for (Employeedto employeeDTO : employeeDTOList) {
                obList.add(new EmployeeTm(
                        employeeDTO.getUserId(),
                        employeeDTO.getId(),
                        employeeDTO.getName(),
                        employeeDTO.getGender(),
                        employeeDTO.getEmail(),
                        employeeDTO.getNic(),
                        employeeDTO.getAddress()
                ));
            }
            tblMian.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        
    }

    public void search_OnAc(ActionEvent actionEvent) {
        try {
            Employeedto employeeDTO = (Employeedto) employeeBo.searchEmployee(txtId.getText());
            if (employeeDTO != null) {
                txtId.setText(employeeDTO.getId());
                txtName.setText(employeeDTO.getName());
                String gender = employeeDTO.getGender();
                if (gender != null && gender.equals("Male")) {
                    chkMale.setSelected(true);
                } else if (gender != null && gender.equals("Female")) {
                    chkFemale.setSelected(true);
                }
                txtEmail.setText(employeeDTO.getEmail());
                txtNic.setText(employeeDTO.getNic());
                txtAddress.setText(employeeDTO.getAddress());
                txtUser.setText(employeeDTO.getUserId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void saveON_Ac(ActionEvent actionEvent) {
        try {
            String userId = txtUser.getText();
            String id = txtId.getText();
            String name = txtName.getText();
            String gender = "";
            if (chkMale.isSelected()) {
                gender = "Male";
                chkFemale.setSelected(false);
            } else if (chkFemale.isSelected()) {
                gender = "Female";
                chkMale.setSelected(false);
            }
            String email = txtEmail.getText();
            String nic = txtNic.getText();
            String address = txtAddress.getText();

            if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || email.isEmpty() || nic.isEmpty() || address.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }
            // Validate email address
            //validateEmail(email);
            boolean isvalid=validateEmployee();
            if(isvalid) {
                // Save employee to database
                boolean isSaved = employeeBo.addEmployee(new Employeedto(userId, id, name, gender, email, nic, address));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                    getAllEmployee();
                }
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Another Correct Informations..!").show();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee to database!").show();
        }
    }

    public void update_OnaC(ActionEvent actionEvent) {
        String userId = txtUser.getText();
        String id = txtId.getText();
        String name = txtName.getText();
        String gender = "";
        if (chkMale.isSelected()) {
            gender = "Male";
            chkFemale.setSelected(false);
        } else if (chkFemale.isSelected()) {
            gender = "Female";
            chkMale.setSelected(false);
        }
        String email = txtEmail.getText();
        String nic = txtNic.getText();
        String address = txtAddress.getText();

        if (userId.isEmpty() || id.isEmpty() || name.isEmpty() || gender.isEmpty() || email.isEmpty() || nic.isEmpty() || address.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }

        try {
            boolean isUpdated = employeeBo.updateEmployee(new Employeedto(userId,id, name, gender, email, nic, address));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                getAllEmployee();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void delete_OnAc(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = employeeBo.deleteEmployee(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
              getAllEmployee();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void validateEmail(String email) {
        // Check if the email address is valid using a regular expression
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Please enter a valid email address!");
        }
    }

    private boolean validateEmployee() {
        String id_value = txtId.getText();
        Pattern complie = Pattern.compile("[C][0-9]{3}");
        Matcher matcher = complie.matcher(id_value);
        boolean matches = matcher.matches();
        if (!matches) {
            new Alert(Alert.AlertType.ERROR, "INVALID Employee ID").show();
            return false;
        }
        String address = txtAddress.getText();
        Pattern compile1 = Pattern.compile("[A-Z]");
        Matcher matcher1 = compile1.matcher(address);
        boolean isAddress = matcher1.matches();
        if (!isAddress) {
            new Alert(Alert.AlertType.ERROR, "WRONG ADDRSS TYPE").show();
        }

        String nameText = txtName.getText();
        boolean isnameValid = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();

        if (!isnameValid) {
            new Alert(Alert.AlertType.ERROR, "WRONG NAME TYPE").show();
        }

        String email = txtEmail.getText();
        boolean isemailvalidete = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$").matcher(email).matches();
        if (!isemailvalidete) {
            new Alert(Alert.AlertType.ERROR, "WRONG Email TYPE").show();
        }
        return true;
    }
}
