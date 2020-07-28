//package server_3_v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;



public class Servidor {
	
	static final int NUM_COLAS = 2;

	static Queue<String>[] colas = new Queue[NUM_COLAS];
	
	
	static Vector<Consumidores> consumidores = new Vector<Consumidores>();
	static Vector<Productores> productores = new Vector<Productores>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0; i<NUM_COLAS; i++) colas[i] = new LinkedList<String>();
		
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
					int cola = Integer.parseInt(partes[1]);
					
					for( Consumidores cons : Servidor.consumidores ) {
						if( cola == cons.cola ) {
							Servidor.consumidores.remove(cons);
							break;
						}
							
					}
					
					Consumidores mtch = new Consumidores( cola, s, dis, dos);
					Thread t = new Thread(mtch);
					consumidores.add(mtch);
					t.start();
					
				}else {
					
					int cola = Integer.parseInt(partes[1]);
					String mensaje = partes[2];
					Servidor.colas[cola].add(mensaje);
					
					for( Consumidores cons : Servidor.consumidores ) {
						if( cola == cons.cola )
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
