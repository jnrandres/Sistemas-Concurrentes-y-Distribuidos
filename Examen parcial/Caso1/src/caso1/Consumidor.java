//package server_1_v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Consumidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Socket s = new Socket("3.19.82.156", 2222);
			
			
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			DataInputStream dis = new DataInputStream(s.getInputStream());
			
			dos.writeUTF("c:");
			
			
			while(true) {
				
				String str = dis.readUTF();
				
				String [] partes =  str.split("::");
				
				
				for(int i=0; i<partes.length; i++) {
					System.out.println("Mensaje del servidor: "+partes[i]);
				}
				
				
			}
			
			
			//dos.close();
			//dis.close();
			//s.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
