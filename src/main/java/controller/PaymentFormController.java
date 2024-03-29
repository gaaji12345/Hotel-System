package controller;

import bo.custom.BookingBo;
import bo.custom.GuesBo;
import bo.custom.PaymentBo;
import bo.custom.RoomBo;
import bo.impl.BookingBoImpl;
import bo.impl.GuestBoImpl;
import bo.impl.PaymentBoImpl;
import bo.impl.RoomBoImpl;
import db.DBConnection;
import dto.Bookingdto;
import dto.Guestdto;
import dto.PaymentDto;
import dto.Roomdto;
import dto.tm.PaymentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PaymentFormController {
    public TableColumn cblGid;
    public TextField txtPyamentId;
    public TextField txtBookingId;
    public Label lblGId;
    public Label lblGname;
    public Label lblRId;
    public Label lblChkIn;
    public Label lblTotPrice;
    public Label lblOAmount;
    public Label lblchkOut;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView <PaymentTm> tblMain;
    public TableColumn cblPId;
    public TableColumn cblBid;
    public TableColumn cblRId;
    public TableColumn cblGname;
    public TableColumn cblChkIn;
    public TableColumn cblChkOut;
    public TableColumn cblAmonut;
    public TableColumn cblTota;

    PaymentBo paymentBO = new PaymentBoImpl();
    BookingBo bookingBO = new BookingBoImpl();
    RoomBo roomBO = new RoomBoImpl();
    GuesBo guestBO = new GuestBoImpl();

    public void initialize() {
        cblPId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        cblGid.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        cblGname.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        cblBid.setCellValueFactory(new PropertyValueFactory<>("resId"));
        cblRId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        cblChkIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        cblChkOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        cblAmonut.setCellValueFactory(new PropertyValueFactory<>("orderAm"));
        cblTota.setCellValueFactory(new PropertyValueFactory<>("total"));
        getAllPayments();
        setValueFactory();
        setSelectToTxt();
        // initUI();
    }

    private void setSelectToTxt() {
        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtPyamentId.setText(newSelection.getPaymentId());
                lblGId.setText(newSelection.getGuestId());
                lblGname.setText(newSelection.getGuestName());
                txtBookingId.setText(newSelection.getResId());
                lblRId.setText(newSelection.getRoomId());
                lblChkIn.setText(newSelection.getCheckIn());
                lblchkOut.setText(newSelection.getCheckOut());
                lblOAmount.setText(String.valueOf(newSelection.getOrderAm()));
                lblTotPrice.setText(String.valueOf(newSelection.getTotal()));
            }
        });
    }

    private void setValueFactory() {

    }

    private void getAllPayments() {

        try {
            ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
            List<PaymentDto> paymentDTOList = paymentBO.getAllPayments();

            for (PaymentDto paymentDTO : paymentDTOList) {
                obList.add(new PaymentTm(paymentDTO.getPaymentId(), paymentDTO.getGuestId(), paymentDTO.getGuestName(), paymentDTO.getResId(), paymentDTO.getRoomId(), paymentDTO.getCheckIn(), paymentDTO.getCheckOut(), paymentDTO.getOrderAm(), paymentDTO.getTotal()));
            }
            tblMain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    public void save_OnAction(ActionEvent actionEvent) {
        String paymentId = txtPyamentId.getText();
        String guestId = lblGId.getText();
        String guestName = lblGname.getText();
        String reservationId = txtBookingId.getText();
        String roomId = lblRId.getText();
        String checkinDate = lblChkIn.getText();
        String checkoutDate = lblchkOut.getText();
        Double ordersAm = Double.valueOf(lblOAmount.getText());
        Double totalPrice = Double.valueOf(lblTotPrice.getText());
        String release = "Available";

        try {
            boolean isSaved = paymentBO.addPayment(new PaymentDto(paymentId,guestId,guestName,reservationId,roomId,checkinDate,checkoutDate,ordersAm,totalPrice));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved!").show();
                roomBO.releaseRoom(roomId,release);
                getAllPayments();
                setValueFactory();

                try {
                    JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Jasper/report.jrxml");
                    JRDesignQuery query=new JRDesignQuery();
                    query.setText("select payment.paymentId, payment.bookingId, payment.guestId, payment.guestName, payment.roomId, room.roomType, payment.checkInDate, payment.checkOutDate, payment.ordersAmount, payment.totalPrice FROM payment INNER JOIN guest ON payment.guestId=guest.guestId INNER JOIN booking ON payment.bookingId=booking.bookingId INNER JOIN room ON payment.roomId=room.roomId WHERE paymentId='"+txtPyamentId.getText()+"';");
                    jasperDesign.setQuery(query);

                    JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint,false);
                } catch (SQLException | JRException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Payment Not saved ,Please Check Details & Try Again!").show();
            //clearTxt();
        }
    }

    public void update_OnAction(ActionEvent actionEvent) {
        String paymentId = txtPyamentId.getText();
        String guestId = lblGId.getText();
        String guestName = lblGname.getText();
        String reservationId = txtBookingId.getText();
        String roomId = lblRId.getText();
        String checkinDate = lblChkIn.getText();
        String checkoutDate = lblchkOut.getText();
        Double ordersAm = Double.valueOf(lblOAmount.getText());
        Double totalPrice = Double.valueOf(lblTotPrice.getText());

        try {
            boolean isUpdated = paymentBO.updatePayment(new PaymentDto(paymentId,guestId,guestName,reservationId,roomId,checkinDate,checkoutDate,ordersAm,totalPrice));
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Payment updated!").show();
                getAllPayments();
                setValueFactory();
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Payment Id Not Exist!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Please Check Details & Try Again!").show();
        }
    }

    public void delete_OnaCTION(ActionEvent actionEvent) {
        String paymentId = txtPyamentId.getText();
        try {
            boolean isDeleted = paymentBO.deletePayment(paymentId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted!").show();
               // clearTxt();
                getAllPayments();
                setValueFactory();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment Id Not Exist!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Please Check Details & Try Again!").show();
           // clearTxt();
        }
    }

    public void txtSearch_onAction(ActionEvent actionEvent) {
        resOnAction();
    }

    private void resOnAction() {
        String id = txtBookingId.getText();
        try {
            Bookingdto res = bookingBO.searchBooking(id);
            Double orderAm = getOrderAmmount(id);

            LocalDate checkIn = LocalDate.parse(res.getCheckIn());
            LocalDate checkOut = LocalDate.parse(res.getCheckOut());
            Roomdto roomDTO = roomBO.searchRoom(res.getRoomId());
            Double price = Double.valueOf(roomDTO.getPrice());
            Double roomPrice = calculateRoomPrice(checkIn,checkOut,price);
            Guestdto guestDTO = guestBO.searchGuest(res.getGuestId());
            Double finalAmmount = orderAm+roomPrice;
            fillResFields(res,orderAm,finalAmmount, guestDTO);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillResFields(Bookingdto res, Double orderAm, Double finalAmmount, Guestdto guestDTO) {
        lblGId.setText(res.getGuestId());
        lblRId.setText(res.getRoomId());
        lblGname.setText(guestDTO.getName());
        lblChkIn.setText(res.getCheckIn());
        lblchkOut.setText(res.getCheckOut());
        lblOAmount.setText(String.valueOf(orderAm));
        lblTotPrice.setText(String.valueOf(finalAmmount));
    }

    private Double calculateRoomPrice(LocalDate checkIn, LocalDate checkOut, Double price) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        return price * days;
    }

    private Double getOrderAmmount(String id) {
        Double orderAmmount = 0.0;
        try {
            orderAmmount = paymentBO.getOrderAmount(id);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        return orderAmmount;
    }
}
