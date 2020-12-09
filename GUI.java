import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane; 
import javafx.scene.text.Text;
import javafx.scene.control.TextField; 
import javafx.geometry.Pos; 
import javafx.geometry.Insets;

public class GUI {
	private TextField textFIp;
	private TextField textFPort;
	private TextField textFKey;
	private TextField textFMsg;
	private Text textStatusMsg;
	private TextField textFOutput;
	private TextField textFPass;

    void init(EventHandler<ActionEvent> eMsg, GridPane root, Stage primaryStage) {
		final int WIDTH = 650;
		final int HEIGHT = 250;

		root.setMinSize(WIDTH,HEIGHT);	
		root.setPadding(new Insets(20));
		root.setVgap(5); 
		root.setHgap(5); 
		root.setAlignment(Pos.CENTER); 
		
		Text textStatus = new Text("Status:");
		textStatusMsg = new Text("OFFLINE");
		Text textKey = new Text("Pad:");
		Text textMsg = new Text("Message:");
		Text textPass = new Text("Password:");
		
		textFIp = new TextField();
		textFPort = new TextField();
		textFKey = new TextField();
		textFMsg = new TextField();
		textFPass = new TextField();
		textFOutput = new TextField("No messages");	

		Button btnMsg = new Button();

		btnMsg.setText("Send Message");
		btnMsg.setOnAction(eMsg);

		root.add(textFIp, 1, 0);
		root.add(textFPort, 1, 1);
		root.add(textPass, 0, 2);
		root.add(textFPass, 1, 2);
		root.add(textStatus, 0, 3);
		root.add(textStatusMsg, 1, 3, 4, 1);
		root.add(textKey, 3, 0);
		root.add(textFKey, 4, 0);
		root.add(textMsg, 3, 1);
		root.add(textFMsg, 4, 1);
		root.add(btnMsg, 6, 2);
		root.add(textFOutput, 0, 5, 5, 5);
	   
		primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
		primaryStage.setTitle("One Time Pad");
		primaryStage.show();
	}
	
	public String getIp() {
		return textFIp.getText();
	}
	
	public String getPort() {
		return textFPort.getText();
	}
	
	public String getKey() {
		return textFKey.getText();
	}
	
	public void setOutput(String s) {
		textFOutput.setText(s);
	}

	public String getMsg() {
		return textFMsg.getText();
	}

	public void setStatus(String s) {
		textStatusMsg.setText(s);
	}
}

