import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML.fxml"));
        Scene scene = new Scene(root, 1280, 650);
        stage.setScene(scene);
        stage.show();
    }
}