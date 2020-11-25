import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import java.lang.NumberFormatException;
import java.io.IOException;

public class Main extends Application {
    
	@Override
	public void start(Stage primaryStage) {
		GUI gui = new GUI();
	
		EventHandler<ActionEvent> msgEvent = new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent e) {
				//add error check for ip
				try {
					String ip = gui.getIp();	
					int port = Integer.parseInt(gui.getPort());
				
					Client c = new Client(ip, port);
				} catch (NumberFormatException fe) {
					gui.setStatus("Invalid port.");
				}

				//make msg using Pad.java
				//create client
				//send request to server
				//if no response, create server and listen
            }
        };

		gui.init(msgEvent, primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
    }
}
