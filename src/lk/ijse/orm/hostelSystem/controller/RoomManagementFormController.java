package lk.ijse.orm.hostelSystem.controller;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lk.ijse.orm.hostelSystem.bo.BOFactory;
import lk.ijse.orm.hostelSystem.bo.custom.RoomBO;
import lk.ijse.orm.hostelSystem.dto.RoomDTO;
import lk.ijse.orm.hostelSystem.util.NotificationController;
import lk.ijse.orm.hostelSystem.util.UILoader;
import lk.ijse.orm.hostelSystem.view.TM.RoomTM;
import org.junit.Test;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class RoomManagementFormController implements Initializable {
    public AnchorPane RoomPane;
    public JFXTextField txtRoomId;
    public JFXTextField txtRoomType;
    public JFXTextField txtMRent;
    public JFXTextField txtQty;
    public TableView<RoomTM> tblRoom;
    public Button btnAddNew;
    public Button btnSave;
    public Button btnDelete;
    public JFXTextField txtSearch;
    public Button btnBack;

    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);

    public void btnSave_OnAction(ActionEvent actionEvent) {

        String id = txtRoomId.getText();
        String type = txtRoomType.getText();
        int qty = Integer.parseInt(txtQty.getText());
        String key_money = txtMRent.getText();

        if (!id.matches("^RM-[0-9]{2,4}$")) {
            NotificationController.Warning("Room ID", "Invalid Room ID.Type RM-000 in your entered value.");
            txtRoomId.requestFocus();
            return;
        } else if (!type.matches("^([A-z]{1,9}|[A-z]{1,9}[ /|-]?[A-z]{1,9}[ /|-]?[A-z]{1,9})$")) {
            NotificationController.Warning("Room Type", "Invalid Room Type.");
            txtRoomType.requestFocus();
            return;
        } else if (!txtMRent.getText().matches("^([A-Z a-z0-9]{4,8})$")) {
            NotificationController.Warning("Room Rent", "Invalid Room Rent.");
            txtMRent.requestFocus();
            return;
        } else if (!txtQty.getText().matches("^[1-9][0-9]*$")) {
            NotificationController.Warning("Room Qty", "Invalid Room Qty");
            txtQty.requestFocus();
            return;
        }
        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Rooms*/
            try {
                if (exitRooms(id)) {
                    NotificationController.Warning_Error("Save Rooms Warning", id, "Already exists ");
                }
                roomBO.saveRooms(new RoomDTO(id, type, key_money, qty));
                tblRoom.getItems().add(new RoomTM(id, type, key_money, qty));
                NotificationController.SuccessfulTableNotification("Save", "Rooms");
            } catch (SQLException e) {
                NotificationController.Warning_Error("Save Rooms Warning", id + e.getMessage(), "Failed to save the Rooms ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update Rooms*/
            try {
                if (!exitRooms(id)) {
                    NotificationController.Warning_Error("Update Rooms Warning", id, "There is no such Rooms associated with the ");
                }
                //Rooms update
                roomBO.updateRooms(new RoomDTO(id, type, key_money, qty));
                NotificationController.SuccessfulTableNotification("Update", "Rooms");
            } catch (SQLException e) {
                NotificationController.Warning_Error("Update Rooms Warning", id + e.getMessage(), "Failed to update the Rooms ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            RoomTM selectedRoom = tblRoom.getSelectionModel().getSelectedItem();
            selectedRoom.setRoom_type_id(id);
            selectedRoom.setType(type);
            selectedRoom.setKey_money(key_money);
            selectedRoom.setQty(qty);
            tblRoom.refresh();
        }
        btnAddNew.fire();
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {

        String code = tblRoom.getSelectionModel().getSelectedItem().getRoom_type_id();
        try {
            if (!exitRooms(code)) {
                new Alert(Alert.AlertType.ERROR, "Warning This Room Already Using by Students. \n Therefore You can't Delete it.!").show();
                NotificationController.Warning_Error("Delete Rooms Warning", code, "This Room Already Using by Students. ");
            }

            roomBO.deleteRooms(code);
            tblRoom.getItems().remove(tblRoom.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Rooms");
            tblRoom.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            NotificationController.Warning_Error("Delete Rooms Warning", code, "Failed to delete the Rooms ");
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        } catch (EntityNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Warning This Room Already Using by Students. \n Therefore You can't Delete it.!").show();
            System.out.println(e);;
        }
    }

    private boolean exitRooms(String id) throws SQLException, ClassNotFoundException {
        return roomBO.existRoomsID(id);
    }

    public void navigateToHome(MouseEvent mouseEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(RoomPane, "DashBoardForm");
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (txtSearch.getText().trim().isEmpty()) {
            NotificationController.Warning("Empty Search Id", "Please Enter Current ID.");
            loadAllRoom();
        } else {
            if (exitRooms(txtSearch.getText())) {
                tblRoom.getItems().clear();
                ArrayList<RoomDTO> arrayList = roomBO.searchAllRooms(txtSearch.getText());
                if (arrayList != null) {
                    for (RoomDTO roomDTO : arrayList) {
                        tblRoom.getItems().add(new RoomTM(roomDTO.getRoom_type_id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
                    }
                }
            } else {
                tblRoom.getItems().clear();
                NotificationController.Warning("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    public void BackOnAction(ActionEvent actionEvent) {
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {

        txtRoomId.setDisable(false);
        txtRoomType.setDisable(false);
        txtMRent.setDisable(false);
        txtQty.setDisable(false);
        txtRoomId.clear();
        txtRoomType.clear();
        txtMRent.clear();
        txtQty.clear();
        txtRoomId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblRoom.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        tblRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("key_money"));
        tblRoom.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        initUI();

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {

                /*----------Text Field Load-------------*/

                txtRoomId.setText(newValue.getRoom_type_id());
                txtRoomType.setText(newValue.getType());
                txtMRent.setText(newValue.getKey_money() + "");
                txtQty.setText(newValue.getQty() + "");

                txtRoomId.setDisable(false);
                txtRoomType.setDisable(false);
                txtMRent.setDisable(false);
                txtQty.setDisable(false);
            }
        });

        txtQty.setOnAction(event -> btnSave.fire());
        loadAllRoom();
    }

    private void loadAllRoom() {
        tblRoom.getItems().clear();
        /*Get all Room*/
        try {
            ArrayList<RoomDTO> allRoom = roomBO.getAllRooms();
            for (RoomDTO roomDTO : allRoom) {
                tblRoom.getItems().add(new RoomTM(roomDTO.getRoom_type_id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtRoomId.clear();
        txtRoomType.clear();
        txtMRent.clear();
        txtQty.clear();

        txtRoomId.setDisable(true);
        txtRoomType.setDisable(true);
        txtMRent.setDisable(true);
        txtQty.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }
}
