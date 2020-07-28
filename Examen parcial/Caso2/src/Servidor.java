//package server_2_v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;


public class Servidor {
	

	static Queue<String> cola = new LinkedList<String>();
	
	static Vector<Consumidores> consumidores = new Vector<Consumidores>();
	static Vector<Productores> productores = new Vector<Productores>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			ServerSocket ss = new ServerSocket(2222);
			Socket s;
			
			
			while(true) {
				s = ss.accept();
				System.out.println("Nueva conexión: "+s);
				
				
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				String str = dis.readUTF();
				String[] partes = str.split(":");
				
				
				if( partes[0].equalsIgnoreCase("c") ) {
					
					for( Consumidores cons : Servidor.consumidores ) {
						if( cons.id == Integer.parseInt(partes[1]) ) {
							Servidor.consumidores.remove(cons);
							break;
						}
					}
					
					Consumidores mtch = new Consumidores(Integer.parseInt( partes[1] ) , s, dis, dos);
					Thread t = new Thread(mtch);
					consumidores.add(mtch);
					t.start();
					
				}else {
					
					String mensaje = partes[1];
					Servidor.cola.add(mensaje);
					
					
					for( Consumidores cons : Servidor.consumidores ) {
						cons.enviarUltimoDato(mensaje);
					}
					
					
					Productores mtch = new Productores(s, dis, dos);
					Thread t = new Thread(mtch);
					productores.add(mtch);
					t.start();
					
				}
				
				//s.close();
				//dis.close();
				//dos.close();
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
