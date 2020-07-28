//package server_3_v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class Productores extends Thread{
	
	
	DataInputStream dis;
	Socket socket;
	DataOutputStream dos;
	int cola;

	public Productores(Socket s, DataInputStream dis, DataOutputStream dos) {
		this.socket = s;
		this.dis = dis;
		this.dos = dos;
	}
	
	public void run() {
		
		for(int i=0; i<Servidor.NUM_COLAS; i++) {
			System.out.println("Longitud de cola: "+ Servidor.colas[i].size());
		}

		while(true) {
			
			String str;
			try {
				
				str = dis.readUTF();
				String[] partes = str.split(":");
				
				
				if( partes[2].equalsIgnoreCase("salir") ) {
					this.socket.close();
					break;
				}


				String mensaje = partes[2];
				int cola = Integer.parseInt(partes[1]);
				Servidor.colas[cola].add(mensaje);
				
				
				for( Consumidores cons : Servidor.consumidores ) {
					if( cola == cons.cola )
						cons.enviarUltimoDato(mensaje);
				}
				
				
				for(int i=0; i<Servidor.NUM_COLAS; i++) {
					System.out.println("Longitud de cola: "+ Servidor.colas[i].size());
				}
				
				
				
				
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
