package sample;

import com.sun.jdi.connect.spi.Connection;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client{

    public String username;
    //socket require IPv6 address as host
    private Socket socket = new Socket("", 1020);
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;
    public ChatController chatController;

    public Client(String username) throws IOException {
        try {
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            listenForMessage();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void sendMessage(String messageToSend) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean messageSent = false;
                    while (socket.isConnected() && !messageSent) {
                        bufferedWriter.write(username + ": " + messageToSend);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        messageSent = true;
                    }
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
    }

    public void listenForMessage() {
        System.out.println("listening for message");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromServer;

                while(socket.isConnected()) {
                    try {
                        messageFromServer = bufferedReader.readLine();
                        System.out.println(messageFromServer);
                        chatController.writeMessage(messageFromServer);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
