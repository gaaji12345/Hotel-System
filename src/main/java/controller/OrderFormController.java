package controller;

import bo.custom.BookingBo;
import bo.custom.FoodBo;
import bo.custom.GuesBo;
import bo.custom.OrderBo;
import bo.impl.BookingBoImpl;
import bo.impl.FoodBoImpl;
import bo.impl.GuestBoImpl;
import bo.impl.OrderBoImpl;
import dto.*;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OrderFormController {

    public TextField txtorderId;
    public ComboBox<String> cmbBId;
    public Label lblgid;
    public Label lblGname;
    public Label lblOrderDate;
    public Label lblRid;
    public ComboBox <String>cmbFoodId;
    public Label lblPrice;
    public Label lblFoodName;
    public TextField txtQty;
    public Button btnCart;
    public Button btnPlaceOrder;
    public TableColumn cblOid;
    public TableColumn cblBid;
    public TableColumn cblGid;
    public TableColumn cblGname;
    public TableColumn cblRid;
    public TableColumn cblOdate;
    public TableColumn cblFid;
    public TableColumn cblPrice;
    public TableColumn cblQty;
    public TableView<OrderTm> tbmMain;
    public TableColumn cblTotal;
    public TableColumn clAction;
    public TableColumn colbtn;
    public TableColumn cblFoodName;

    private ObservableList<OrderTm> obList = FXCollections.observableArrayList();
    BookingBo bookingBO = new BookingBoImpl();
    GuesBo guestBO = new GuestBoImpl();
    FoodBo foodBO = new FoodBoImpl();
    OrderBo orderBO = new OrderBoImpl();

    public void initialize() {
        cblOid.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        cblBid.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        cblGid.setCellValueFactory(new PropertyValueFactory<>("guestID"));
        cblGname.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        cblOdate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        cblFid.setCellValueFactory(new PropertyValueFactory<>("FoodID"));
        cblFoodName.setCellValueFactory(new PropertyValueFactory<>("FoodName"));
        cblPrice.setCellValueFactory(new PropertyValueFactory<>("FoodPrice"));
        cblQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        cblTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        clAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
        loadBookingIds();
        loadFoodIds();
        setOrderDate();
        setValueFactory();
       // initUI();
    }

    private void setValueFactory() {
    }

    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
        
    }

    private void loadFoodIds() {
        try {
            ArrayList<Fooddto> allFoods = foodBO.getAllFoods();
            for (Fooddto c : allFoods) {
                cmbFoodId.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load food ids").show();
        }
        
    }

    private void loadBookingIds() {
        try {
            ArrayList<Bookingdto> allBookings = bookingBO.getAllBookings();
            for (Bookingdto c : allBookings) {
                cmbBId.getItems().add(c.getBookingId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load booking ids").show();
        }
        
    }

    public void cart_oNaction(ActionEvent actionEvent) {
        if (!txtQty.getText().isBlank() && !txtorderId.getText().isBlank()) {
            String code = cmbFoodId.getValue();
            String description = lblFoodName.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblPrice.getText());
            double total = qty * unitPrice;
            Button removeBtn = new Button("Remove");
            removeBtn.setCursor(Cursor.HAND);
            setRemoveBtnOnAction(removeBtn);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tbmMain.getItems().size(); i++) {
                    if (cblFid.getCellData(i).equals(code)) {
                        qty += (int) cblQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTotal(total);

                        tbmMain.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            OrderTm tm = new OrderTm(txtorderId.getText(), cmbBId.getValue(), lblgid.getText(), lblGname.getText(), lblOrderDate.getText(), code, description, unitPrice, qty, total, removeBtn);

            obList.add(tm);
            tbmMain.setItems(obList);
            calculateNetTotal();
        }
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tbmMain.getItems().size(); i++) {
            double total = (double) cblTotal.getCellData(i);
            netTotal += total;
        }

    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index=tbmMain.getItems().size()-1;
                obList.remove(index);

                tbmMain.refresh();
                calculateNetTotal();
            }
        });
    }

    public void placeOrder_oNaction(ActionEvent actionEvent) {
        boolean b = saveOrder(txtorderId.getText(), lblOrderDate.getText(), cmbBId.getValue(), tbmMain.getItems().stream().map(tm -> new OrderDetailsdto(tm.getOrderID(), tm.getFoodID(), tm.getQty(), tm.getTotal(), tm.getOrderDate())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        txtorderId.setText(generateNewOrderId());
        cmbBId.getSelectionModel().clearSelection();
        cmbFoodId.getSelectionModel().clearSelection();
        tbmMain.getItems().clear();
        txtQty.clear();
        calculateNetTotal();
    }
    private boolean saveOrder(String orderId, String date, String bookingId, List<OrderDetailsdto> orderDetails) {
     //   boolean isValdate = validateOrder();
       // if (isValdate) {
            return orderBO.saveOrder(new Orderdto(orderId, date, bookingId, orderDetails));

    }

    private String generateNewOrderId() {
        try {
            return orderBO.generateNewOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "O00-001";
    }



    public void book_IDonAction(ActionEvent actionEvent) {

        String id = cmbBId.getValue();
        try {
            Bookingdto res = (Bookingdto) bookingBO.searchBooking(id);
            String code = res.getGuestId();
            Guestdto ges = (Guestdto) guestBO.searchGuest(code);
            String gesCode = ges.getName();
            fillBookFields(res,gesCode);
            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillBookFields(Bookingdto res, String gesCode) {
        lblgid.setText(res.getGuestId());
        lblRid.setText(res.getRoomId());
        lblGname.setText(gesCode);
    }

    public void food_OnAction(ActionEvent actionEvent) {
        String id = cmbFoodId.getValue();
        try {
            Fooddto foodDTO = (Fooddto) foodBO.searchFood(id);
            fillMealFields(foodDTO);

            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillMealFields(Fooddto foodDTO) {
        lblFoodName.setText(foodDTO.getName());
        lblPrice.setText(String.valueOf(foodDTO.getPrice()));
    }

    private boolean validateOrder() {
        String id_value=txtorderId.getText();
        Pattern complie=Pattern.compile("[O][0-9]{3}");
        Matcher matcher=complie.matcher(id_value);
        boolean matches=matcher.matches();
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"INVALID ODER ID").show();
            return  false;
        }
//

        return true;

    }
}
