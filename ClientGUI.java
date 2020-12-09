import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane; 
import java.io.IOException;
import java.lang.NumberFormatException;
import java.net.ConnectException;

public class ClientGUI extends Application {
		
	GUI gui = new GUI();
	Client client;

	public void start(Stage primaryStage) {
		GridPane root = new GridPane();

		Button btnConnect = new Button();
		btnConnect.setText("Connect");
		btnConnect.setOnAction(eConnect);

		Button btnDisconnect = new Button();
		btnDisconnect.setText("Disconnect");
		btnDisconnect.setOnAction(eDisconnect);
		
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
				client = new Client(gui.getConnectIp(), Integer.parseInt(gui.getConnectPort()), gui);
				client.connect();
			} 
			catch (NumberFormatException e) {
				gui.setStatus("Invalid port.");
				return;
			}
			catch (IOException e) {
				gui.setStatus("Failed to connect." + e.getMessage());
				return;
			}

			//Platform.run
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
				client.sendData(Pad.encrypt(gui.getMsg(),gui.getKey()));
			}
			catch (IOException e) {
				gui.setStatus("Failed to send the message." + e.getMessage());
				return;
			}

			gui.setStatus("Sent the encrypted message.");
		}
	};

}

