//package server_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Productores extends Thread{
	
	
	DataInputStream dis;
	Socket socket;
	DataOutputStream dos;
	int cola;

	public Productores(int cola,Socket s, DataInputStream dis, DataOutputStream dos) {
		this.socket = s;
		this.dis = dis;
		this.dos = dos;
		this.cola = cola;
	}
	
	public void run() {
		
		for(int i=0; i<Servidor.NUM_COLAS; i++) {
			System.out.println("Longitud de cola ["+i+"] : "+ Servidor.colas[i].size());
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
				Servidor.colas[cola-1].add(mensaje);
				String facto = Servidor.factorial( Integer.parseInt(mensaje)  );
				Servidor.colas[cola].add(  facto );
				
				
				for( Consumidores cons: Servidor.consumidores ) {
					if ( cons.cola == cola-1 ) {
						cons.enviarUltimoDato(mensaje);
					}
				}
				
				for(int i=0; i<Servidor.NUM_COLAS; i++) {
					System.out.println("Longitud de cola ["+i+"] : "+ Servidor.colas[i].size());
				}
				
				dos.writeUTF(facto+"::");
				
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
