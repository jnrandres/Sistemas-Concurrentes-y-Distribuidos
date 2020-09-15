package peruflix_servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServidor {
	
	public static void main (String[] args) throws IOException {
		
		int RTSP_port = 8554;
		
		
		@SuppressWarnings("resource")
		ServerSocket RTSP_server_socket = new ServerSocket(RTSP_port);
		
		Socket socket;
		
		
		while(true) {
			
			socket = RTSP_server_socket.accept();
			
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			ControladorClientes mtch = new ControladorClientes();
			mtch.RTSP_socket = socket;
			mtch.dis = dis;
			mtch.dos = dos;
			mtch.cliente_ip = socket.getInetAddress();
			
			Thread t = new Thread(mtch);
			t.start();
			
		}
		
	}
	
	

}
