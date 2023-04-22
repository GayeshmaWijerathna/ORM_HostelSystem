package lk.ijse.orm.hostelSystem.controller;


import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.orm.hostelSystem.util.UILoader;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class DashBoardFormController {

    public ImageView imgStudent;
    public ImageView imgRooms;
    public ImageView imgKeyMoney;
    public Label lblMenu;
    public Label lblDescription;
    public AnchorPane AdminDashboard;
    public Button btnBack;


    public void userOnAction(ActionEvent actionEvent) throws IOException {

        UILoader.loadUiDashBoard(AdminDashboard,"ManageUserForm");
    }

    public void navigate(MouseEvent mouseEvent) throws IOException {

        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            Parent root = null;

            switch (icon.getId()) {
                case "imgStudent":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/orm/hostelSystem/view/ReservationForm.fxml")));
                    break;
                case "imgRooms":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/orm/hostelSystem/view/RoomManagementForm.fxml")));
                    break;
                case "imgKeyMoney":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/orm/hostelSystem/view/FindKeyMoneyForm.fxml")));
                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.AdminDashboard.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();
            }
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgStudent":
                    lblMenu.setText("Registration");
                    lblDescription.setText("Click to Make Registration and Add, Update, Delete Student");
                    break;
                case "imgRooms":
                    lblMenu.setText("Rooms Management");
                    lblDescription.setText("Click to Add, Update, Delete Rooms");
                    break;
                case "imgKeyMoney":
                    lblMenu.setText("Key Money Payments");
                    lblDescription.setText("Click to find Remain key money from students");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.BLACK);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);

            lblMenu.setText("");
            lblDescription.setText("");
        }
    }

    public void BackOnAction(ActionEvent actionEvent) throws SQLException, IOException {
      ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(no) == ok) {
            UILoader.NavigateToHome(AdminDashboard, "LoginForm");
     }
    }
}
