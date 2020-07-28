//package server_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;


public class Servidor {
	
	static final int NUM_CONEXIONES = 2;
	static final int NUM_COLAS = 2*NUM_CONEXIONES;
	static Queue<String>[] colas = new Queue[NUM_COLAS];
	
	
	static Vector<Consumidores> consumidores = new Vector<Consumidores>();
	static Vector<Productores> productores = new Vector<Productores>();
	
	
	public static String factorial (int n) {
		double prod = 1.0;
		for(int i=1; i<=n; i++) {
			prod *= i;
		}
		return Double.toString(prod);
	}
	
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
				System.out.println("Message: "+str);
				
				String[] partes = str.split(":");
				
				
				if( partes[0].equalsIgnoreCase("c") ) {
					
					Consumidores mtch = new Consumidores( Integer.parseInt(partes[1])*2 ,s, dis, dos);
					Thread t = new Thread(mtch);
					consumidores.add(mtch);
					t.start();
					
				}else {
					
					int cola = Integer.parseInt(partes[1])*2;
					
					String mensaje = partes[2];
					Servidor.colas[cola].add(mensaje);
					String facto = Servidor.factorial( Integer.parseInt(mensaje)  );
					Servidor.colas[cola+1].add(  facto );
					
					for( Consumidores cons: Servidor.consumidores ) {
						if ( cons.cola == cola ) {
							cons.enviarUltimoDato(mensaje);
						}
					}
					
					try {
						if( Servidor.colas[cola+1].size()  != 0 ) {
							String salida = "";
							for( String item: Servidor.colas[cola+1] ) {
								salida += item + "::";
							}
							dos.writeUTF(salida);
						}else dos.writeUTF("Vacio");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Productores mtch = new Productores(cola + 1, s, dis, dos);
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
