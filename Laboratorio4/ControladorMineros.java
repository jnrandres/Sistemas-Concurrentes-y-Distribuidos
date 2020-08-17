package version_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ControladorMineros extends Thread{
	
	DataInputStream dis;
	Socket socket;
	DataOutputStream dos;
	int id;
	
	public ControladorMineros (int id, Socket socket, DataInputStream dis, DataOutputStream dos) {
		
		this.id = id;
		this.socket = socket;
		this.dis = dis;
		this.dos = dos;
		
	}
	
	public void run () {
		
		String dataRecibida;
		
		try {
			dos.writeUTF( Servidor.palabra + ":" + Servidor.numeroCeros );
			dataRecibida = dis.readUTF();
			
			if( !Servidor.ganador ) {
				// Llego primero
				System.out.println( "Llego primero [" + this.id + " : "+dataRecibida+"]");
				
				String [] parametros = dataRecibida.split(":");
				Servidor.llaveGanadora = parametros[0]; // Guargando la llave ganadora
				
				Servidor.ganador = true;
				
			}else {
				// Verificar con los otros  mineros
				dos.writeUTF("verifica:"+Servidor.palabra+":"+Servidor.llaveGanadora);
				dataRecibida = dis.readUTF();
				System.out.println( this.id + ":"+dataRecibida);
				
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
			
		
		
	}

}
