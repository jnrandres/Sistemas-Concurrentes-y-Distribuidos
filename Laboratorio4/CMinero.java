package version_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CMinero {
	
	static boolean confirmacion = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			System.out.println("Estableciendo conexion con el servidor...");
			
			@SuppressWarnings("resource")
			Socket s = new Socket("localhost", 3000);
			
			System.out.println("Conectado con el servidor.");
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			
			while(true) {
				
				System.out.println("Esperando palabra para minar");
				String palabras = dis.readUTF();
				
				String [] parametros = palabras.split(":");
				
				if( parametros[0].equalsIgnoreCase("verifica") ) { // verifica:palabra:llave
					
					String word = parametros[1];
					String llave = parametros[2];
					System.out.println("Llave: "+llave);
					System.out.println("Palabra: "+word);
					SHAone sha = new SHAone();
					
					String z,hash="";
			        byte[] dataBuffer;
			        System.out.println("Minando ...");
			        z = word + llave ;
		        	dataBuffer = z.getBytes();
		        	hash = sha.Encript(dataBuffer);
		            System.out.println("Salida [" + llave + "]: " +  hash);
		            
		            
			        String enviarStr = "Verificado";
			        dos.writeUTF( enviarStr );
			        
			        
				}else {
					
					SHAone sha = new SHAone();
					int sumz = 0;
					int zeros = Integer.parseInt(parametros[1]);
					
					String z="";
					String hash="";
					String llave = "";
			        byte[] dataBuffer;
			        String palabra = parametros[0];

			        long lRandom = Long.parseUnsignedLong("100000000000");
			        long startTime = System.nanoTime();
			        
			        System.out.println("Minando ...");
			        
			        while( sumz != zeros ) {
			        	llave = String.valueOf( (long) (Math.random() * lRandom ));
			        	z = palabra +  llave;
			        	dataBuffer = z.getBytes();
			        	hash = sha.Encript(dataBuffer);
			        	sumz = 0;
			            for (int j = 0; j < zeros; j++) {
			                if (hash.charAt(j) == '0')
			                	sumz = sumz + 1;
			            }
			        }
			        
			        System.out.println("Dato encontrado");
			        System.out.println("Salida [" + llave + "]: " + hash);
			        long endTime   = System.nanoTime();
			        double totalTime = (endTime - startTime) / 1000000.0;
			        System.out.println("Tiempo de demora: "+totalTime+" ms");
			        String enviarStr = llave+ ": " + Double.toString(totalTime) + " ms";
			        
			        dos.writeUTF( enviarStr );
					
				}
	        
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
