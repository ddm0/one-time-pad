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
		InetAddress addr;

		if (ip != null && !ip.isEmpty()) {
			addr = InetAddress.getByName(ip);
		} else {
			addr = InetAddress.getLocalHost();
		}

		serversocket = new ServerSocket(port, 1, addr);
	}

	void stop() throws IOException {
		serversocket.close();
	}

	void sendData(String data) throws IOException { 
		output.writeChars(data);
	}
	
	String listen() throws IOException {
		socket = serversocket.accept();
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		return input.readUTF();
	}
}

