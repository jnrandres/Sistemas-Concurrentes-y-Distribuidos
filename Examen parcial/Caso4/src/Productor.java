//package server_4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class Productor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Socket s = new Socket("localhost", 2222);
			
			
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			DataInputStream dis = new DataInputStream(s.getInputStream());
			
			String id = "1";
			
			Random rand = new Random();
			Scanner scanner = new Scanner(System.in);
			
			String numero = "nn";
			
			
			while(true) {
				
				numero = scanner.nextLine();
				dos.writeUTF("p:"+id+":"+numero);
				System.out.println("p:"+id+":"+numero);
				
				if(numero.equalsIgnoreCase("salir")) break;
				
				String str = dis.readUTF();
				String[] partes = str.split("::");
				for(int i=0; i<partes.length; i++)
					System.out.println("Productor ["+id+"] y el mensaje del servidor: "+partes[i]);
				
			}
			
			
			dos.close();
			dis.close();
			s.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
