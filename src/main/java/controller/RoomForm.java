package controller;


import bo.custom.RoomBo;
import bo.impl.RoomBoImpl;
import dto.Roomdto;
import dto.tm.RoomTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

public class RoomForm {

    public AnchorPane root;
    public TextField txtId;
    public TextField txtdetail;
    public TextField txtType;
    public TextField txtPrice;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;
    public TableView <RoomTm> tblMain;
    public TableColumn cblid;
    public TableColumn cblDetail;
    public TableColumn cblType;
    public TableColumn cblPrice;

    RoomBo roomBo=new RoomBoImpl();

    public  void initialize(){
        cblid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cblDetail.setCellValueFactory(new PropertyValueFactory<>("details"));
        cblType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        cblPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadAllrooms();
        setSelectToTxt();
        initUI();

    }

    private void initUI() {
        txtPrice.setOnAction(event -> btnSave.fire());
    }

    private void setSelectToTxt() {
        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtdetail.setText(newSelection.getDetails());
                txtType.setText(newSelection.getRoomType());
                txtPrice.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }

    private void loadAllrooms() {
        try {
            ObservableList<RoomTm> obList = FXCollections.observableArrayList();
            List<Roomdto> roomDTOList = roomBo.getAllRooms();

            for (Roomdto roomDTO : roomDTOList) {
                obList.add(new RoomTm(
                        roomDTO.getId(),
                        roomDTO.getDetails(),
                        roomDTO.getRoomType(),
                        roomDTO.getPrice()
                ));
            }
            tblMain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void search_OnAc(ActionEvent actionEvent) {
        try {
            Roomdto roomDTO = roomBo.searchRoom(txtId.getText());
            if (roomDTO != null) {
                txtId.setText(roomDTO.getId());
                txtdetail.setText(roomDTO.getDetails());
                txtType.setText(roomDTO.getRoomType());
                txtPrice.setText(String.valueOf(roomDTO.getPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void saveOnAc(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            String details = txtdetail.getText();
            String roomType =txtType.getText();
            Double price = Double.parseDouble(txtPrice.getText());

            if (id.isEmpty() || details.isEmpty() || roomType.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isSaved = roomBo.addRoom(new Roomdto(id, details, roomType, price));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Room saved!").show();
                loadAllrooms();
            }
        } catch (IllegalArgumentException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void updateOnAc(ActionEvent actionEvent) {
        String id = txtId.getText();
        String details = txtdetail.getText();
        String roomType =txtType.getText();
        Double price = Double.parseDouble(txtPrice.getText());

        try {
            boolean isUpdated = roomBo.updateRoom(new Roomdto(id, details, roomType, price));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Room updated!").show();
                loadAllrooms();
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Room updated!").show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void deleteOnAc(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            boolean isDeleted = roomBo.deleteRoom(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                loadAllrooms();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void clearOnAc(ActionEvent actionEvent) {
        txtId.clear();
        txtPrice.clear();
        txtdetail.clear();
        txtType.clear();
    }
}
