package lk.ijse.orm.hostelSystem.controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LogingController {
    public Button binlogID;
    public Label lblHide;
    public JFXPasswordField txtPassword;
    public Button cancel;

    public void loginOnAction(ActionEvent actionEvent) {




    }

    //------Show Password-----//
    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("/lk/ijse/orm/hostelSystem/assets/Image/show-password.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPassword.setPromptText(txtPassword.getText());
        txtPassword.setText("");
        txtPassword.setDisable(true);
        txtPassword.requestFocus();
    }

    //------Hide Password-----//
    public void hidePasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("/lk/ijse/orm/hostelSystem/assets/Image/hide-password.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPassword.setText(txtPassword.getPromptText());
        txtPassword.setPromptText("");
        txtPassword.setDisable(false);
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}


