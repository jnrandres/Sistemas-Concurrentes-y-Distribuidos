package peruflix_cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class MainCliente {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Cliente cliente = new Cliente();

		InetAddress serverIP = InetAddress.getByName("localhost");
		cliente.RTSP_socket = new Socket(serverIP, Cliente.RTSP_port);
		
		cliente.RTP_port = 1237;
		cliente.video_seleccionado = 1;
		
		cliente.dis = new DataInputStream(cliente.RTSP_socket.getInputStream());
		cliente.dos = new DataOutputStream(cliente.RTSP_socket.getOutputStream());
		
		cliente.estado = Cliente.INIT;
	}

}
