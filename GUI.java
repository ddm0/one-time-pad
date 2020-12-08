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
	private TextField textFLocalIp;
	private TextField textFConnectIp;
	private TextField textFLocalPort;
	private TextField textFConnectPort;
	private TextField textFKey;
	private TextField textFMsg;
	private Text textStatusMsg;
	private TextField textOutput;

    void init(EventHandler<ActionEvent> eMsg, GridPane root, Stage primaryStage) {
		final int WIDTH = 300;
		final int HEIGHT = 250;

		root.setMinSize(WIDTH,HEIGHT);	
		root.setPadding(new Insets(20));
		root.setVgap(5); 
		root.setHgap(5); 
		root.setAlignment(Pos.CENTER); 
		
		Text textLocalIp = new Text("Local IP:");
		Text textConnectIp = new Text("Connect IP:");
		Text textLocalPort = new Text("Local PORT:");
		Text textConnectPort = new Text("Connect PORT:");
		Text textStatus = new Text("Status: ");
		textStatusMsg = new Text("OFFLINE");
		Text textKey = new Text("Pad:");
		Text textMsg = new Text("Message:");
		
		textFLocalIp = new TextField();
		textFConnectIp = new TextField();
		textFLocalPort = new TextField();
		textFConnectPort = new TextField();
		textFKey = new TextField();
		textFMsg = new TextField();
		textOutput = new TextField("No message recieved.");	
		textOutput.setPrefWidth(80);

		Button btnMsg = new Button();
		btnMsg.setText("Send Message");
		btnMsg.setOnAction(eMsg);

		root.add(textLocalIp, 0, 0);
		root.add(textFLocalIp, 1, 0);
		root.add(textLocalPort, 0, 1);
		root.add(textFLocalPort, 1, 1);
		root.add(textConnectIp, 0, 2);
		root.add(textFConnectIp, 1, 2);
		root.add(textConnectPort, 0, 3);
		root.add(textFConnectPort, 1, 3);
		root.add(textStatus, 0, 4);
		root.add(textStatusMsg, 1, 4, 4, 1);
		root.add(textKey, 3, 0);
		root.add(textFKey, 4, 0);
		root.add(textMsg, 3, 1);
		root.add(textFMsg, 4, 1);
		root.add(btnMsg, 4, 2);
		root.add(textOutput, 0, 5, 5, 5);
	   
		primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
		primaryStage.setTitle("OTP");
		primaryStage.show();
	}
	
	public String getLocalIp() {
		return textFLocalIp.getText();
	}
	
	public String getConnectIp() {
		return textFConnectIp.getText();
	}
	
	public String getLocalPort() {
		return textFLocalPort.getText();
	}
	
	public String getConnectPort() {
		return textFConnectPort.getText();
	}
	
	public String getKey() {
		return textFKey.getText();
	}
	
	public void setOutput(String s) {
		textOutput.setText(s);
	}

	public String getMsg() {
		return textFMsg.getText();
	}

	public void setStatus(String s) {
		textStatusMsg.setText(s);
	}
}

