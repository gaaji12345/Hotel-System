package controller;

import bo.custom.BookingBo;
import bo.custom.GuesBo;
import bo.custom.RoomBo;
import bo.impl.BookingBoImpl;
import bo.impl.GuestBoImpl;
import bo.impl.RoomBoImpl;
import dao.custom.RoomDao;
import dto.Bookingdto;
import dto.Guestdto;
import dto.Roomdto;
import dto.tm.BookTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingFormController {
    public AnchorPane root;
    public ComboBox cmbGuestId;
    public TextField txtbid;
    public DatePicker dateBookinDate;
    public ComboBox cmbRoomid;
    public DatePicker dchkIn;
    public DatePicker dchkOut;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView <BookTm> tblMain;
    public TableColumn cblGid;
    public TableColumn cblBid;
    public TableColumn cblBdate;
    public TableColumn cblRid;
    public TableColumn cblChkIn;
    public TableColumn cblChkOut;
    
    BookingBo bookingBo=new BookingBoImpl();
    RoomBo roomBo=new RoomBoImpl();
    GuesBo guesBo=new GuestBoImpl();


    public void initialize() {
        cblGid.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        cblBid.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        cblBdate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        cblRid.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        cblChkIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        cblChkOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        loadallBooking();
        loadGuestIds();
        loadRoomIds();
        setSelectToTxt();
    }

    private void loadRoomIds() {
        try {
            ArrayList<Roomdto> allRooms = roomBo.getAllRooms();
            for (Roomdto c : allRooms) {
                cmbRoomid.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load booking ids").show();
        }
    }

    private void loadGuestIds() {
        try {
            ArrayList<Guestdto> allGuests = guesBo.getAllGuests();
            for (Guestdto c : allGuests) {
                cmbGuestId.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load booking ids").show();
        }
        
    }

    private void setSelectToTxt() {
        tblMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbGuestId.setValue(newSelection.getGuestId());
                txtbid.setText(newSelection.getBookingId());
                dateBookinDate.setValue(LocalDate.parse(newSelection.getBookingDate()));
                cmbRoomid.setValue(newSelection.getRoomId());
                dchkIn.setValue(LocalDate.parse(newSelection.getCheckIn()));
                dchkOut.setValue(LocalDate.parse(newSelection.getCheckOut()));
            }
        });
    }

    private void loadallBooking() {
        try {
            ObservableList<BookTm> obList = FXCollections.observableArrayList();
            List<Bookingdto> bookingDTOList = bookingBo.getAllBookings();

            for (Bookingdto bookingDTO : bookingDTOList) {
                obList.add(new BookTm(
                        bookingDTO.getGuestId(),
                        bookingDTO.getBookingId(),
                        bookingDTO.getBookingDate(),
                        bookingDTO.getRoomId(),
                        bookingDTO.getCheckIn(),
                        bookingDTO.getCheckOut()
                ));
            }
            tblMain.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }


    public void save_OnAction(ActionEvent actionEvent) {
        try {
            String guestId = (String) cmbGuestId.getValue();
            String bookingId = txtbid.getText();
            LocalDate selectedBookingDate = dateBookinDate.getValue();
            String bookingDate = selectedBookingDate.toString();
            String roomId = (String) cmbRoomid.getValue();
            LocalDate selectedInDate = dchkIn.getValue();
            String checkIn = selectedInDate.toString();
            LocalDate selectedOutDate = dchkOut.getValue();
            String checkOut = selectedOutDate.toString();

            if (guestId.isEmpty() || bookingId.isEmpty() || bookingDate.isEmpty() || roomId.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isSaved = bookingBo.addBooking(new Bookingdto(guestId, bookingId, bookingDate, roomId, checkIn, checkOut));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking saved!").show();
                bookingBo.releaseRoom(roomId);
                loadallBooking();
            }
        } catch (IllegalArgumentException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void update_OnAction(ActionEvent actionEvent) {
        try {
            String guestId = (String) cmbGuestId.getValue();
            String bookingId = txtbid.getText();
            String roomId = (String) cmbRoomid.getValue();
            LocalDate selectedOutDate = dchkOut.getValue();
            String checkOut = selectedOutDate.toString();

            if (guestId.isEmpty() || bookingId.isEmpty() || roomId.isEmpty() || checkOut.isEmpty()) {
                throw new IllegalArgumentException("Please fill out all the required fields!");
            }

            boolean isUpdated = bookingBo.updateBooking(new Bookingdto(guestId, roomId, checkOut, bookingId));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking updated!").show();
                loadallBooking();
            }
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void delete_OnAc(ActionEvent actionEvent) {
        String id = txtbid.getText();
        try {
            boolean isDeleted = bookingBo.deleteBooking(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
              loadallBooking();
               // initUI();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }
}
