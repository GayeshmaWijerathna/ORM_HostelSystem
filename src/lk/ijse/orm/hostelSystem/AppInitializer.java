package lk.ijse.orm.hostelSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.util.resources.cldr.ru.LocaleNames_ru_UA;

import java.io.IOException;
import java.util.Objects;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/lk/ijse/orm/hostelSystem/view/Loging.fxml")))));
        //   primaryStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/lk/hostelManagement/pos/view/LoginForm.fxml")))));
        primaryStage.centerOnScreen();
     //   primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
}

