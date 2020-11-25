import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.Proxy;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Client {
	private	SocketAddress addr;
	private	Proxy proxy; 
	private	Socket socket;
	private	InetSocketAddress dest;
	private DataInputStream input;
	private DataOutputStream output;
		
	Client(String ip, int port) {
		//tor
		addr = new InetSocketAddress("127.0.0.1", 9050);
		proxy = new Proxy(Proxy.Type.SOCKS, addr);

		dest = new InetSocketAddress(ip, port);
		socket = new Socket(proxy);
	}

	void connect() throws IOException {
		socket.connect(dest);
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
	}

	void stop() throws IOException {
		socket.close();
	}

	void sendData(String data) throws IOException { 
		output.writeChars(data);
	}
	
	String getData() throws IOException {
		return input.readUTF();
	}
}
