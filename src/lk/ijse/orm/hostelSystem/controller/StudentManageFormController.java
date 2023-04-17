package lk.ijse.orm.hostelSystem.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.orm.hostelSystem.bo.BOFactory;
import lk.ijse.orm.hostelSystem.bo.custom.StudentBO;
import lk.ijse.orm.hostelSystem.dto.StudentDTO;
import lk.ijse.orm.hostelSystem.util.NotificationController;
import lk.ijse.orm.hostelSystem.util.UILoader;
import lk.ijse.orm.hostelSystem.view.TM.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentManageFormController implements Initializable {

    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtContactNo;
    public JFXTextField txtAddress;
    public JFXComboBox cmbGender;
    public JFXDatePicker datePickerDOB;
    public TableView <StudentTM>tblStudent;
    public Button btnAddNew;
    public Button btnSave;
    public Button btnDelete;
    public JFXTextField txtSearch;
    public Button btnBack1;
    public AnchorPane StudentPane;

    public void btnAddNew_OnAction(ActionEvent actionEvent) {

        txtId.setDisable(false);
        txtName.setDisable(false);
        txtContactNo.setDisable(false);
        txtAddress.setDisable(false);
        datePickerDOB.setDisable(false);
        cmbGender.setDisable(false);

        txtId.clear();
        txtName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        datePickerDOB.getEditor().clear();
        cmbGender.getSelectionModel().clearSelection();
        txtId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblStudent.getSelectionModel().clearSelection();
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

        String id = txtId.getText();
        String name = txtName.getText();
        String cNO = txtContactNo.getText();
        String address = txtAddress.getText();
        LocalDate dob = datePickerDOB.getValue();
        String gender = (String) cmbGender.getValue();


        if (!id.matches("^(SID-[0-9]{3,4})$")) {
            NotificationController.Warning("Student Id", "Invalid Student Id.Type SID-000 in your entered value.");
            txtId.requestFocus();
            return;
        } else if (!name.matches("^([A-Z a-z]{5,40})$")) {
            NotificationController.Warning("Student Name", "Student Name.");
            txtName.requestFocus();
            return;
        } else if (!cNO.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            NotificationController.Warning("Contact Number", "Invalid Student Contact Number.");
            txtContactNo.requestFocus();
            return;
        } else if (!address.matches("^([A-Za-z]{4,10})$")) {
            NotificationController.Warning("Address", "Invalid Student Address.");
            txtAddress.requestFocus();
            return;
        } else if (!gender.matches("^([A-Z a-z]{4,20})$")) {
            NotificationController.Warning("Gender", "Invalid Student Gender.");
            cmbGender.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Student*/
            try {
                if (exitStudent(id)) {
                    NotificationController.Warning_Error("Save Student Warning", id, "Already exists ");
                }
                studentBO.saveStudent(new StudentDTO(id, name, address, cNO, dob, gender));
                tblStudent.getItems().add(new StudentTM(id, name, address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Save", "Student");
            } catch (SQLException e) {
                NotificationController.Warning_Error("Save Student Warning", id + e.getMessage(), "Failed to save the Student ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update Rooms*/
            try {
                if (!exitStudent(id)) {
                    NotificationController.Warning_Error("Update Student Warning", id, "There is no such Student associated with the ");
                }
                //Rooms update
                studentBO.updateStudent(new StudentDTO(id, name, address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Update", "Student");
            } catch (SQLException e) {
                NotificationController.Warning_Error("Update Student Warning", id + e.getMessage(), "Failed to update the Student ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
            selectedItem.setStudent_id(id);
            selectedItem.setName(name);
            selectedItem.setAddress(address);
            selectedItem.setContact_no(cNO);
            selectedItem.setDob(dob);
            selectedItem.setGender(gender);
            tblStudent.refresh();
        }
        btnAddNew.fire();
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {

        String code = tblStudent.getSelectionModel().getSelectedItem().getStudent_id();;
        try {
            if (!exitStudent(code)) {
                NotificationController.Warning_Error("Delete Student Warning", code, "There is no such Student associated with the ");
            }
            studentBO.deleteStudent(code);
            tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Student");
            tblStudent.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            NotificationController.Warning_Error("Delete Student Warning", code, "Failed to delete the Student ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllStudent() {
        tblStudent.getItems().clear();
        /*Get all Student*/
        try {
            ArrayList<StudentDTO> allStudent = studentBO.getAllStudent();
            for (StudentDTO studentDTO : allStudent) {
                tblStudent.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact_no(), studentDTO.getDob(), studentDTO.getGender()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void BackOnAction(ActionEvent actionEvent) {
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (txtSearch.getText().trim().isEmpty()) {
            NotificationController.Warning("Empty Search Id", "Please Enter Current ID.");
            loadAllStudent();
        } else {
            if (exitStudent(txtSearch.getText())) {
                tblStudent.getItems().clear();
                ArrayList<StudentDTO> arrayList = studentBO.searchAllStudent(txtSearch.getText());
                if (arrayList != null) {
                    for (StudentDTO studentDTO : arrayList) {
                        tblStudent.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact_no(), studentDTO.getDob(), studentDTO.getGender()));
                    }
                }
            } else {
                tblStudent.getItems().clear();
                NotificationController.Warning("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    public void navigateToHome(MouseEvent mouseEvent) throws SQLException, IOException {

        UILoader.NavigateToHome(StudentPane, "ReservationForm");
    }

    private void initUI() {
        txtId.clear();
        txtName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        datePickerDOB.getEditor().clear();
        cmbGender.getSelectionModel().clearSelection();

        txtId.setDisable(true);
        txtName.setDisable(true);
        txtContactNo.setDisable(true);
        txtAddress.setDisable(true);
        datePickerDOB.setDisable(true);
        cmbGender.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbGender.getItems().addAll("Male", "Female", "Other");

        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtId.setText(newValue.getStudent_id());
                txtName.setText(newValue.getName());
                txtContactNo.setText(newValue.getContact_no());
                txtAddress.setText(newValue.getAddress());
                datePickerDOB.setValue(newValue.getDob());
                cmbGender.setValue(newValue.getGender() + "");

                txtId.setDisable(false);
                txtName.setDisable(false);
                txtContactNo.setDisable(false);
                txtAddress.setDisable(false);
                datePickerDOB.setDisable(false);
                cmbGender.setDisable(false);
            }
        });

        txtAddress.setOnAction(event -> btnSave.fire());
        loadAllStudent();
    }

    private boolean exitStudent(String id) throws SQLException, ClassNotFoundException {
        return studentBO.existStudentID(id);
    }
}
