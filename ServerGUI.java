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
			try {
				server = new Server(gui.getLocalIp(), Integer.parseInt(gui.getLocalPort()));
				server.start();
			} 
			catch (NumberFormatException e) {
				gui.setStatus("Invalid port.");
				return;
			}
			catch (IOException e) {
				gui.setStatus("Failed to start the server.");
				return;
			}

			listener = new Listener(server, gui);
			listener.run();
		}
	};
	
	EventHandler<ActionEvent> eDestroy = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			listener.exit();
			
			try {
				server.stop();
			}
			catch (IOException e) {}
				
			gui.setStatus("Stopped the server.");
		}
	};
	
	EventHandler<ActionEvent> eMsg = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent ae) {
			try {
				server.sendData(Pad.encrypt(gui.getMsg(), gui.getKey()));
			}
			catch (IOException e) {
				gui.setStatus("Failed to send the message.");
			}

			gui.setStatus("Sent the encrypted message");
		}
	};

}

