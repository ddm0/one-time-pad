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
	TextField textFIp;
	TextField textFPort;
	TextField textFPad;
	TextField textFMsg;
	Text textStatus;
	TextField textOutput;

    public void init(EventHandler<ActionEvent> msgEvent, Stage primaryStage) {
		final int WIDTH = 300;
		final int HEIGHT = 250;

		GridPane root = new GridPane();
		root.setMinSize(WIDTH,HEIGHT);	
		root.setPadding(new Insets(20));
		root.setVgap(5); 
		root.setHgap(5); 
		root.setAlignment(Pos.CENTER); 
		
		Text textIp = new Text("IP:");
		Text textPort = new Text("PORT:");
		Text textStatusMsg = new Text("Status: ");
		textStatus = new Text("OFFLINE");
		Text textPad = new Text("Pad:");
		Text textMsg = new Text("Message:");
		
		textFIp = new TextField();
		textFPort = new TextField();
		textFPad = new TextField();
		textFMsg = new TextField();
		textOutput = new TextField("No message recieved.");	
		textOutput.setPrefWidth(80);

		Button btnMsg = new Button();
		btnMsg.setText("Send Message");
		btnMsg.setOnAction(msgEvent);

		root.add(textIp, 0, 0);
		root.add(textFIp, 1, 0);
		root.add(textPort, 0, 1);
		root.add(textFPort, 1, 1);
		root.add(textStatusMsg, 0, 2);
		root.add(textStatus, 1, 2);
		root.add(textPad, 3, 0);
		root.add(textFPad, 4, 0);
		root.add(textMsg, 3, 1);
		root.add(textFMsg, 4, 1);
		root.add(btnMsg, 4, 2);
		root.add(textOutput, 0, 5, 5, 5);
	   
		primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
		primaryStage.setTitle("OTP");
		primaryStage.show();
	}
	
	public String getIp() {
		return textFIp.getText();
	}
	
	public String getPort() {
		return textFPort.getText();
	}
	
	public String getPad() {
		return textFPad.getText();
	}

	public String getMsg() {
		return textFMsg.getText();
	}

	public void setStatus(String s) {
		textStatus.setText(s);
	}
}

