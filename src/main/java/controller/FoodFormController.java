package controller;

import bo.custom.FoodBo;
import bo.impl.FoodBoImpl;
import dto.Fooddto;
import dto.tm.FoodTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodFormController {
    public AnchorPane root;
    public TextField txtId;
    public TextField txtName;
    public TextField txtDesc;
    public TextField txtPrice;
    public Button btnsave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView <FoodTm> tblMain;
    public TableColumn cblId;
    public TableColumn cblName;
    public TableColumn cblDetail;
    public TableColumn cblPrice;
    FoodBo foodBo=new FoodBoImpl();

    public void initialize() {
        cblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cblName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cblDetail.setCellValueFactory(new PropertyValueFactory<>("details"));
        cblPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        getAllFoods();
        setSelectToTxt();

    }

    private void setSelectToTxt() {
        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtDesc.setText(newSelection.getDetails());
                txtPrice.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }

    private void getAllFoods() {
        try {
            ObservableList<FoodTm> obList = FXCollections.observableArrayList();
            List<Fooddto> foodDTOList = foodBo.getAllFoods();

            for (Fooddto foodDTO : foodDTOList) {
                obList.add(new FoodTm(
                        foodDTO.getId(),
                        foodDTO.getName(),
                        foodDTO.getDetails(),
                        foodDTO.getPrice()
                ));
            }
            tblMain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    public void txtSerachOn_Ac(ActionEvent actionEvent) {
        try {
            Fooddto foodDTO = (Fooddto) foodBo.searchFood(txtId.getText());
            if (foodDTO != null) {
                txtId.setText(foodDTO.getId());
                txtName.setText(foodDTO.getName());
                txtDesc.setText(foodDTO.getDetails());
                txtPrice.setText(String.valueOf(foodDTO.getPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void save_OnAction(ActionEvent actionEvent) {
        try{
            String id = txtId.getText();
            String name = txtName.getText();
            String details =txtDesc.getText();
            Double price = Double.parseDouble(txtPrice.getText());

            boolean isvalid=validateFood();
             if (isvalid) {

                 if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
                     throw new IllegalArgumentException("Please fill out all the required fields!");
                 }

                 boolean isSaved = foodBo.addFood(new Fooddto(id, name, details, price));
                 if (isSaved) {
                     new Alert(Alert.AlertType.CONFIRMATION, "Food saved!").show();
                     getAllFoods();
                 }
             }else {
                 new Alert(Alert.AlertType.ERROR,"Enter right details").show();
             }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save Food to database!").show();
        }
    }

    public void update_OnAc(ActionEvent actionEvent) {

        String id = txtId.getText();
        String name = txtName.getText();
        String details =txtDesc.getText();
        Double price = Double.parseDouble(txtPrice.getText());

        if (id.isEmpty() || name.isEmpty() || details.isEmpty()) {
            throw new IllegalArgumentException("Please fill out all the required fields!");
        }

        try {
            boolean isUpdated = foodBo.updateFood(new Fooddto(id, name, details, price));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Food updated!").show();
               getAllFoods();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void delete_OnAc(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = foodBo.deleteFood(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
               getAllFoods();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    private boolean validateFood() {
        String id_value=txtId.getText();
        Pattern complie=Pattern.compile("[C][0-9]{3}");
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
