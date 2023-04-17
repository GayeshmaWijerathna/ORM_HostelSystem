package lk.ijse.orm.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;


public class LoginFormController {

    public Label lblHide;
    public AnchorPane logging_pane;
    public JFXTextField UserName_Id;
    public JFXPasswordField Pasword_Id;


    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {

        Image img = new Image("/lk/ijse/orm/view/assests/images/HidePW.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        Pasword_Id.setPromptText(Pasword_Id.getText());
        Pasword_Id.setText("");
        Pasword_Id.setDisable(true);
        Pasword_Id.requestFocus();
    }

    public void LoggingOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

    }

    public void hidePasswordOnMousePressedd(MouseEvent mouseEvent) {
        Image img = new Image("/lk/ijse/orm/view/assests/images/ShowPW.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        Pasword_Id.setText(Pasword_Id.getPromptText());
        Pasword_Id.setPromptText("");
        Pasword_Id.setDisable(false);
    }
}
