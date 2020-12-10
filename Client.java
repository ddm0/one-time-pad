import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.Proxy; 
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javafx.application.Platform; 

public class Client extends Thread {
	private	SocketAddress addr;
	private	Proxy proxy; 
	private	Socket socket;
	private	InetSocketAddress dest;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean running = false;
	private GUI gui;
	private String print;

	Client() {}
		
	Client(String ip, int port, GUI gui) {
		//tor
		addr = new InetSocketAddress("127.0.0.1", 9050);
		proxy = new Proxy(Proxy.Type.SOCKS, addr);

		dest = new InetSocketAddress(ip, port);
		socket = new Socket(proxy);
		this.gui = gui;
	}

	void connect() throws IOException {
		socket.connect(dest, 1000);
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		start();
	}

	void disconnect() throws IOException {
		socket.close();
		running = false;
	}

	void sendData(String data) throws IOException { 
		output.writeUTF(data);
	}
	
	public void run() {
		running = true;

		while (running) {	
			try {
				print = input.readUTF();
				if (print.equals("DISCONNECT")) {
					throw new IOException();
				}
				print = Pad.decrypt(print);
				Platform.runLater(new Runnable() {
					public void run() {
						gui.setOutput(print);
					}
				});
			}
			catch (IOException e) {
				Platform.runLater(new Runnable() {
					public void run() {
						gui.setStatus("Disconnected.");
					}
				});
				running = false;
			}
		}
	}

	public boolean isRunning() {
		return running;
	}
}

