package version_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Servidor {

	static Vector <ControladorMineros> ar = new Vector<>();
	static int i = 0;
	static String palabra = "null";
	static String numeroCeros = "1";
	
	static boolean ganador = false;
	static String llaveGanadora = "";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(3000);
			Socket s;
			
			while(true) {
				s = ss.accept();
				
				System.out.println("Solicitud de nuevo minero, recibida: "+s);
				
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				System.out.println("Creando nuevo contralador para este minero.");
				
				ControladorMineros mtch = new ControladorMineros(i, s, dis, dos);
		
				System.out.println("Añadiendo al minero en el vector de mineros en linea.");
				ar.add(mtch);
				i++;
				
				
				System.out.println("¿Iniciar el minado (SI / NO)?");
				String res = scanner.nextLine();
				
				if( res.equalsIgnoreCase("SI") ) {
					
					
					while( true ) {
						
						System.out.println("Ingrese la palabra: ");
						Servidor.palabra = scanner.nextLine();
						
						System.out.println("Ingrese el número de ceros: ");
						Servidor.numeroCeros = scanner.nextLine();
						
						Servidor.ganador = false;
					
						
						for( ControladorMineros cm: Servidor.ar ) {
							Thread t = new Thread (cm);
							t.start();
						}
						
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
