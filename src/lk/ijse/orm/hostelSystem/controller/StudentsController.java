package lk.ijse.orm.hostelSystem.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class StudentsController {
    public AnchorPane StudentPane;
    public JFXTextField txtID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public JFXDatePicker dateOfBirth;
    public JFXComboBox comGender;
    public TableView tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colDob;
    public TableColumn colGender;
    public TableColumn colContactNo;
    public JFXTextField txtSearch;

    public void SearchOnAction(MouseEvent mouseEvent) {
    }

    public void AddOnAction(ActionEvent actionEvent) {
    }

    public void SaveOnAction(ActionEvent actionEvent) {
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
    }

    public void BackOnAction(ActionEvent actionEvent) {
    }
}
