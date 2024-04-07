package controller;

import bo.custom.SupplierBo;
import bo.impl.SupplierBoImpl;
import dto.Supplierdto;
import dto.tm.SupplierTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierFormController {

    public TextField txtid;
    public TextField txtName;
    public TextField txtDetail;
    public TextField txtTel;
    public Button btnsave;
    public Button btnupdate;
    public Button btndelete;
    public TableView <SupplierTm>tblmain;
    public TableColumn cblid;
    public TableColumn cblname;
    public TableColumn cbltel;
    public TableColumn cbldetail;

    SupplierBo supplierBo =new SupplierBoImpl();

    public void initialize() {
        cblid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cblname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cbltel.setCellValueFactory(new PropertyValueFactory<>("contact"));
        cbldetail.setCellValueFactory(new PropertyValueFactory<>("details"));
        //setCellValueFactory();
        getAllSuppliers();
        setSelectToTxt();
        //initUI();
    }

    private void getAllSuppliers() {
        try {
            ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
            List<Supplierdto> supplierDTOList = supplierBo.getAllSuppliers();

            for (Supplierdto supplierDTO : supplierDTOList) {
                obList.add(new SupplierTm(
                        supplierDTO.getId(),
                        supplierDTO.getName(),
                        supplierDTO.getContact(),
                        supplierDTO.getDetails()
                ));
            }
            tblmain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setSelectToTxt() {
        tblmain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtid.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtTel.setText(newSelection.getContact());
                txtDetail.setText(newSelection.getDetails());
            }
        });
    }

    public void search_On(ActionEvent actionEvent) {
        try {
            Supplierdto supplierDTO = supplierBo.searchSupplier(txtid.getText());
            if (supplierDTO != null) {
                txtid.setText(supplierDTO.getId());
                txtName.setText(supplierDTO.getName());
                txtTel.setText(supplierDTO.getContact());
                txtDetail.setText(supplierDTO.getDetails());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void save_OnAc(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();
            String name = txtName.getText();
            String contact =txtTel.getText();
            String details = txtDetail.getText();

            if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || details.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }
            boolean isvalid=validateSupplier();
            if (isvalid) {

                boolean isSaved = supplierBo.addSupplier(new Supplierdto(id, name, contact, details));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved!").show();
                    getAllSuppliers();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"ENTER RIGHT DETAILS...!").show();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void update_OnAc(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();
            String name = txtName.getText();
            String contact =txtTel.getText();
            String details = txtDetail.getText();

            if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || details.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isUpdated = supplierBo.updateSupplier(new Supplierdto(id, name, contact, details));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
           getAllSuppliers();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void delete_OnAc(ActionEvent actionEvent) {
        String id = txtid.getText();
        try {
            boolean isDeleted = supplierBo.deleteSupplier(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
               getAllSuppliers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    private boolean validateSupplier() {
        String id_value=txtid.getText();
        Pattern complie=Pattern.compile("[S][0-9]{3}");
        Matcher matcher=complie.matcher(id_value);
        boolean matches=matcher.matches();
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"INVALID FOOD ID").show();
            return  false;
        }
//
        String nameText=txtName.getText();
        boolean isnameValid= Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();

        if (!isnameValid){
            new Alert(Alert.AlertType.ERROR,"WRONG NAME TYPE").show();
        }
        return true;

    }
}
