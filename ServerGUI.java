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

public class ServerGUI extends Application {
		
	GUI gui = new GUI();
	Server server = new Server();

	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
		
		Text textLocalIp = new Text("Local IP:");
		Text textLocalPort = new Text("Local PORT:");
		
		Button btnCreate = new Button();
		btnCreate.setText("Create Server");
		btnCreate.setOnAction(eCreate);

		Button btnDestroy = new Button();
		btnDestroy.setText("Destroy Server");
		btnDestroy.setOnAction(eDestroy);
		
		root.add(textLocalIp, 0, 0);
		root.add(textLocalPort, 0, 1);
		root.add(btnCreate, 6, 0);
		root.add(btnDestroy, 6, 1);

		gui.init(eMsg, root, primaryStage);
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				try {
					server.close();
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

	EventHandler<ActionEvent> eCreate = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (server.isRunning()) {
				gui.setStatus("Server already running.");	
				return;
			}

			try {
				if (gui.getKey().equals("")) {
					gui.setStatus("Invalid key");
					return;
				}
				server = new Server(gui.getIp(), Integer.parseInt(gui.getPort()), gui);
				server.open();
			} 
			catch (NumberFormatException e) {
				gui.setStatus("Invalid port.");
				return;
			}
			catch (IOException e) {
				gui.setStatus("Failed to start the server. " + e.getMessage());
				return;
			}
			
			gui.setStatus("Successfully started the server.");
		}
	};
	
	EventHandler<ActionEvent> eDestroy = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (!server.isRunning()) {
				gui.setStatus("No server running.");	
				return;
			}
			
			try {
				server.close();
			}
			catch (IOException e) {
				gui.setStatus("Failed to stop the server. " + e.getMessage());
				return;
			}
				
			gui.setStatus("Successfully stopped the server.");
		}
	};
	
	EventHandler<ActionEvent> eMsg = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (!server.isRunning()) {
				gui.setStatus("No server running.");	
				return;
			}

			if (gui.getMsg() == null || gui.getMsg().isEmpty()) {
				gui.setStatus("Invalid message.");
				return;
			}

			try {
				if (Pad.canEncrypt(gui.getMsg())){
					server.sendData(Pad.encrypt(gui.getMsg()));
				} else {
					gui.setStatus("Message too long for the remaining characters in the key.");
					return;
				}
			}
			catch (Exception e) {
				gui.setStatus("Failed to send the message.");
				Pad.undo(gui.getMsg().length());
				return;
			}

			gui.setStatus("Sent the encrypted message");
		}
	};

}

