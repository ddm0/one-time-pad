import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane; 
import java.io.IOException;
import java.lang.NumberFormatException;
import java.net.ConnectException;

public class ServerGUI extends Application {
		
	GUI gui = new GUI();
	Server server;
	boolean serverAlive = false;
	Listener listener = new Listener(server, gui);

	public void start(Stage primaryStage) {
		GridPane root = new GridPane();

		Button btnCreate = new Button();
		btnCreate.setText("Create Server");
		btnCreate.setOnAction(eCreate);

		Button btnDestroy = new Button();
		btnDestroy.setText("Destroy Server");
		btnDestroy.setOnAction(eDestroy);
		
		root.add(btnCreate, 6, 0);
		root.add(btnDestroy, 6, 1);

		gui.init(eMsg, root, primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}


	EventHandler<ActionEvent> eCreate = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (serverAlive) {
				gui.setStatus("Server already running.");	
				return;
			}

			try {
				server = new Server(gui.getLocalIp(), Integer.parseInt(gui.getLocalPort()));
			} 
			catch (NumberFormatException e) {
				gui.setStatus("Invalid port.");
				return;
			}
			catch (IOException e) {
				gui.setStatus("Failed to start the server." + e.getMessage());
				return;
			}
			

			listener = new Listener(server, gui);
			listener.start();

			gui.setStatus("Successfully started the server.");
			serverAlive = true;
		}
	};
	
	EventHandler<ActionEvent> eDestroy = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (!serverAlive) {
				gui.setStatus("No server running.");	
				return;
			}

			listener.exit();
			
			try {
				server.stop();
			}
			catch (IOException e) {
				gui.setStatus("Failed to stop the server." + e.getMessage());
				return;
			}
				
			gui.setStatus("Successfully stopped the server.");
			serverAlive = false;
		}
	};
	
	EventHandler<ActionEvent> eMsg = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			if (!serverAlive) {
				gui.setStatus("No server running.");	
				return;
			}

			try {
				server.sendData(Pad.encrypt(gui.getMsg(), gui.getKey()));
			}
			catch (Exception e) {
				gui.setStatus("Failed to send the message." + e.getMessage());
			}

			gui.setStatus("Sent the encrypted message");
		}
	};

}

