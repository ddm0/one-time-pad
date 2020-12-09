import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javafx.application.Platform; 

public class Server extends Thread {
	private InetAddress addr;
	private int port;
	private Socket socket;
	private ServerSocket serversocket;
	private DataInputStream input; private DataOutputStream output;
	private boolean running = false;
	private GUI gui;

	public String print;

	Server() {}

	Server(String ip, int port, GUI gui) throws IOException {
		if (ip != null && !ip.isEmpty()) {
			addr = InetAddress.getByName(ip);
		} else {
			addr = InetAddress.getLocalHost();
		}
		this.port = port;
		this.gui = gui;
	}

	void open() throws IOException {
		serversocket = new ServerSocket(port, 1, addr); 
		start();
	}

	void close() throws IOException {
		running = false;
		serversocket.close();
	}

	void sendData(String data) throws IOException { 
		output.writeUTF(data);
	}
	
	public void run() {
		running = true;

		while (running) {
			try {
				socket = serversocket.accept();
				output = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());
			}
			catch (IOException e) {}
			while (running) {	
				try {
					print = input.readUTF();
					Platform.runLater(new Runnable() {
						public void run() {
							gui.setOutput(print);
						}
					});
				}
				catch (IOException e) {
					break;
				}
			}
		}
	}

	public boolean isRunning() {
		return running;
	}
		
/*

		if (firstConnection) {
				socket = serversocket.accept();
				output = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());
				firstConnection = false;
		} else if (!socket.getInetAddress().isReachable(500)) {
				socket = serversocket.accept();
				output = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());
		}
		*/
			//}
			/*
			byte[] buffer = new byte[1024];
			int read;
			String out = "";
			while((read = input.read(buffer)) != -1) {
				out += new String(buffer, 0, read);
			};

			*/
			//return input.readUTF();
	
		//return out;
	}


