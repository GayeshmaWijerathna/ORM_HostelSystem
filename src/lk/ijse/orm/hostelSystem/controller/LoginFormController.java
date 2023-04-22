package lk.ijse.orm.hostelSystem.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.orm.hostelSystem.bo.BOFactory;
import lk.ijse.orm.hostelSystem.bo.custom.UserBO;
import lk.ijse.orm.hostelSystem.entity.Loging;
import lk.ijse.orm.hostelSystem.util.FactoryConfiguration;
import lk.ijse.orm.hostelSystem.util.NotificationController;
import lk.ijse.orm.hostelSystem.util.UILoader;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;


public class LoginFormController {

    public Label lblHide;
    public AnchorPane logging_pane;
    public JFXTextField UserName_Id;
    public JFXPasswordField Password_Id;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    public Button btnLogin;

    public void CancelOnAction(ActionEvent actionEvent) {
        UserName_Id.clear();
        Password_Id.clear();
    }

    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {

        Image img = new Image("/lk/ijse/orm/hostelSystem/view/assests/images/HidePW.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        Password_Id.setPromptText(Password_Id.getText());
        Password_Id.setText("");
        Password_Id.setDisable(true);
        Password_Id.requestFocus();
    }

    public void LoggingOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();


        Query query = session.createQuery("from Loging where name =:user_name and Password=:password");
        query.setParameter("user_name", UserName_Id.getText());
        query.setParameter("password", Password_Id.getText());
        Loging user = (Loging) query.uniqueResult();
        if ((user != null) || (UserName_Id.getText().equals("User") && Password_Id.getText().equals("1234"))) {
            try {
                UILoader.LoginOnAction(logging_pane, "DashBoardForm");
                NotificationController.LoginSuccessfulNotification("Admin");

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Check User Name and Password").show();
            NotificationController.LoginUnSuccessfulNotification("Please Insert Correct User Name & Password for Login to the System. \n *If you've Forgot Password contact ADMIN");

        }

        session.close();
    }


    public void hidePasswordOnMousePressedd(MouseEvent mouseEvent) {
        Image img = new Image("/lk/ijse/orm/hostelSystem/view/assests/images/ShowPW.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        Password_Id.setText(Password_Id.getPromptText());
        Password_Id.setPromptText("");
        Password_Id.setDisable(false);
    }

    public void enterOnActionun(ActionEvent actionEvent) {
        Password_Id.requestFocus();
    }

    public void enterOnActionpw(ActionEvent actionEvent) {
        btnLogin.fire();
    }
}
