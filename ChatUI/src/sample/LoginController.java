package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginController {

    @FXML
    public Stage stage;
    @FXML
    public Scene scene;
    @FXML
    public Parent root;
    @FXML
    public TextField usernameField;
    @FXML
    public Button loginButton;


    public void login(ActionEvent actionEvent) throws IOException {
        if (usernameField.getText().trim().isEmpty()) {
            System.out.println("Please enter username.");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ChatScene.fxml"));
            root = loader.load();
            ChatController controller = loader.getController();
            controller.initClientData(new Client(usernameField.getText().trim()));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setMinWidth(600);
            stage.setMinHeight(690);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void loginFromKey(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (usernameField.getText().trim().isEmpty()) {
                System.out.println("Please enter username.");
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ChatScene.fxml"));
                root = loader.load();
                ChatController controller = loader.getController();
                controller.initClientData(new Client(usernameField.getText().trim()));
                stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();
                stage.setResizable(false);
                stage.setMinWidth(600);
                stage.setMinHeight(690);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
}
