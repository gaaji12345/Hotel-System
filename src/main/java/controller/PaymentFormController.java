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
import dto.PaymentDto;
import dto.tm.PaymentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
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
}
