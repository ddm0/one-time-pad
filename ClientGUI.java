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

public class ClientGUI extends Application {
		
	GUI gui = new GUI();
	Client client;

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
	}

	public static void main(String[] args) {
		launch(args);
	}

	EventHandler<ActionEvent> eConnect = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			try {
				client = new Client(gui.getIp(), Integer.parseInt(gui.getPort()), gui);
				client.connect();
				Pad.setKey(gui.getKey());
			} 
			catch (NumberFormatException e) {
				gui.setStatus("Invalid port.");
				return;
			}
			catch (IOException e) {
				gui.setStatus("Failed to connect." + e.getMessage());
				return;
			}

			gui.setStatus("Connected to the server.");
		}
	};
	
	EventHandler<ActionEvent> eDisconnect = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			try {
				client.disconnect();
			}
			catch (IOException e) {
				gui.setStatus("Failed to disconnect." + e.getMessage());
			}
			gui.setStatus("Disconnected from the server.");
		}
	};
	
	EventHandler<ActionEvent> eMsg = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			try {
				client.sendData(Pad.encrypt(gui.getMsg()));
			}
			catch (IOException e) {
				gui.setStatus("Failed to send the message." + e.getMessage());
				return;
			}

			gui.setStatus("Sent the encrypted message.");
		}
	};

}

