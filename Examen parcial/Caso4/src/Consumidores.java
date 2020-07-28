//package server_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Consumidores extends Thread{
	
	
	DataInputStream dis;
	Socket socket;
	DataOutputStream dos;
	int cola;
	int ultima_pos;

	public Consumidores(int cola,Socket s, DataInputStream dis, DataOutputStream dos) {
		this.socket = s;
		this.dis = dis;
		this.dos = dos;
		this.cola = cola;
	}
	
	public void run() {
		
		
		try {
			if( Servidor.colas[cola].size()  != 0 ) {
				
				String salida = "";
				for( String s: Servidor.colas[cola] ) {
					salida += s + "::";
				}
				
				dos.writeUTF(salida);
				
			}else dos.writeUTF("Vacio");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void enviarUltimoDato (String mensaje) {
		try {
			dos.writeUTF(mensaje+"::");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
