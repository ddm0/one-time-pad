import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Server extends Listenable {
	private InetAddress ip;
	private int port;
	private Socket socket;
	private ServerSocket serversocket;
	private DataInputStream input;
	private DataOutputStream output;

	Server(String ip, int port) throws IOException {
		if (ip != null && !ip.isEmpty()) {
			this.ip = InetAddress.getByName(ip);
		} else {
			this.ip = InetAddress.getLocalHost();
		}
		this.port = port;
	}

	void start() throws IOException {
		serversocket = new ServerSocket(port, 1, ip);
		socket = serversocket.accept();
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
	}

	void stop() throws IOException {
		socket.close();
		serversocket.close();
	}

	void sendData(String data) throws IOException { 
		output.writeChars(data);
	}
	
	String listen() throws IOException {
		return input.readUTF();
	}
}

