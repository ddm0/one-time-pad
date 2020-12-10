import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane; 
import java.io.IOException;
import java.lang.NumberFormatException;
import java.net.ConnectException;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import javafx.application.Platform;

public class ClientGUI extends Application {
		
	GUI gui = new GUI();
	Client client = new Client();

	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
		
		Text textConnectIp = new Text("Connect IP:");
		Text textConnectPort = new Text("Connect PORT:");

		Button btnConnect = new Button();
		btnConnect.setText("Connect");
		btnConnect.setOnAction(eConnect);

		Button btnDisconnect = new Button();
		btnDisconnect.setText("Disconnect");
		btnDisconnect.setOnAction(eDisconnect);
		
		root.add(textConnectIp, 0, 0);
		root.add(textConnectPort, 0, 1);
		root.add(btnConnect, 6, 0);
		root.add(btnDisconnect, 6, 1);
		
		gui.init(eMsg, root, primaryStage);
	
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				try {
					client.disconnect();
				}
				catch (Exception e) {}
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	EventHandler<ActionEvent> eConnect = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (client.isRunning()) {
				gui.setStatus("Already connected to server.");
			}

			try {
				if (gui.getKey().equals("")) {
					gui.setStatus("Invalid key");
					return;
				}
				Pad.setKey(gui.getKey());
				client = new Client(gui.getIp(), Integer.parseInt(gui.getPort()), gui);
				client.connect();
			} 
			catch (NumberFormatException e) {
				gui.setStatus("Invalid port.");
				return;
			}
			catch (IOException e) {
				gui.setStatus("Failed to connect. " + e.getMessage());
				return;
			}

			gui.setStatus("Connected to the server.");
		}
	};
	
	EventHandler<ActionEvent> eDisconnect = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (!client.isRunning()) {
				gui.setStatus("Not connected to the server.");	
				return;
			}

			try {
				client.disconnect();
			}
			catch (IOException e) {
				gui.setStatus("Failed to disconnect. " + e.getMessage());
			}
			gui.setStatus("Disconnected from the server.");
		}
	};
	
	EventHandler<ActionEvent> eMsg = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (!client.isRunning()) {
				gui.setStatus("Not connected to the server.");	
				return;
			}

			if (gui.getMsg() == null || gui.getMsg().isEmpty()) {
				gui.setStatus("Invalid message.");
				return;
			}

			try {
				if (Pad.canEncrypt(gui.getMsg())){
					client.sendData(Pad.encrypt(gui.getMsg()));
				} else {
					gui.setStatus("Message too long for the remaining characters in the key.");
					return;
				}
			}
			catch (IOException e) {
				gui.setStatus("Failed to send the message. " + e.getMessage());
				Pad.undo(gui.getMsg().length());
				return;
			}

			gui.setStatus("Sent the encrypted message.");
		}
	};
}

