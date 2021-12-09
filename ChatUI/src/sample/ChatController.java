package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatController {

    @FXML
    public TextArea chatArea;
    @FXML
    public TextArea messageArea;
    @FXML
    public Button sendButton;

    public Client client;

    public void initClientData(Client client) {
        this.client = client;
        this.client.setUsername(client.getUsername());
        this.client.setChatController(this);

    }

    public void sendMessageFromKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            String messageToSend = messageArea.getText().trim();
            this.client.sendMessage(messageToSend);
            messageArea.clear();
        }
    }

    public void sendMessageFromButton(ActionEvent actionEvent) {
        String messageToSend = messageArea.getText();
        this.client.sendMessage(messageToSend);
        messageArea.clear();
    }

    public void writeMessage(String message) {
        chatArea.appendText(message + "\n");
    }
}
