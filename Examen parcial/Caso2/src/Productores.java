//package server_2_v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class Productores extends Thread{
	
	
	DataInputStream dis;
	Socket socket;
	DataOutputStream dos;

	public Productores(Socket s, DataInputStream dis, DataOutputStream dos) {
		this.socket = s;
		this.dis = dis;
		this.dos = dos;
	}
	
	public void run() {
		
		System.out.println("Longitud de cola: "+ Servidor.cola.size());

		while(true) {
			
			String str;
			try {
				
				str = dis.readUTF();
				String[] partes = str.split(":");
				
				
				if( partes[1].equalsIgnoreCase("salir") ) {
					this.socket.close();
					break;
				}


				String mensaje = partes[1];
				Servidor.cola.add(mensaje);
				
				
				for( Consumidores cons : Servidor.consumidores ) {
					cons.enviarUltimoDato(mensaje);
				}
				
				System.out.println("Longitud de cola: "+ Servidor.cola.size());
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		try{ 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
		
		
		
	}
	
	
}
