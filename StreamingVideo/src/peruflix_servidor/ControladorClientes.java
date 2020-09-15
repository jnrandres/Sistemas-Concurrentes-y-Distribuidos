package peruflix_servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import servidor.CtrlServidor;

public class ControladorClientes implements Runnable{

	// Variables RTP
	DatagramSocket RTP_socket;
	DatagramPacket RTP_senddp;
	InetAddress cliente_ip;
	int FRAME_PERIODO = 50;
	int VIDEO_LENGHT;
	String VIDEO_NAME;
	int RTP_port;
	int frames;
	BufferedImage bImage;
	ByteArrayOutputStream bos;
	byte [] buffer;
	File sourceImage;
	Timer timer;
	
	// Estados de RTSP
	static int INIT = 0;
	static int READY = 1;
	static int PLAYING = 2;
	
	// Tipos de mensajes RTSP
	static int SETUP = 3;
	static int PLAY = 4;
	static int PAUSE = 5;
	static int TEARDOWN = 6;
	
	int estado; 
	Socket RTSP_socket;
	DataInputStream dis;
	DataOutputStream dos;
	
	int velocidad = 1;
	
	public ControladorClientes() throws SocketException, UnknownHostException {
		// TODO Auto-generated constructor stub
		timer = new Timer(FRAME_PERIODO, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					frames+=velocidad;
					if(frames <= VIDEO_LENGHT) {
						if( frames < 10) {
							sourceImage = new File(VIDEO_NAME+"/salida_0000"+Integer.toString(frames)+".jpeg");
						}else {
							if( frames < 100 ) {
								sourceImage = new File(VIDEO_NAME+"/salida_000"+Integer.toString(frames)+".jpeg");
							}else {
								if( frames < 1000 ) {
									sourceImage = new File(VIDEO_NAME+"/salida_00"+Integer.toString(frames)+".jpeg");
								}else {
									if( frames < 10000 ) {
										sourceImage = new File(VIDEO_NAME+"/salida_0"+Integer.toString(frames)+".jpeg");
									}else {
										sourceImage = new File(VIDEO_NAME+"/salida_0"+Integer.toString(frames)+".jpeg");
									}
								}
							}
						}
						bImage = ImageIO.read(sourceImage);
						bos = new ByteArrayOutputStream();
						ImageIO.write(bImage,"jpeg",bos);
						buffer = bos.toByteArray();
						RTP_senddp = new DatagramPacket( buffer, 
													 	 buffer.length,
													 	 cliente_ip, 
													 	 RTP_port);
						RTP_socket.send(RTP_senddp);
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		} );
		
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		frames=0;
		System.out.println("Servidor iniciado");
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		estado = INIT;

		int request_type;
		boolean done = false;
		
		while(!done) {
			request_type = parse_RTSP_request();
			
			if( request_type == SETUP ) {
				done = true;
				
				estado = READY;
				
				send_RTSP_response();
				
				try {
					RTP_socket = new DatagramSocket();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		while(true) {
			request_type = parse_RTSP_request();
			
			if( (request_type==PLAY) && (estado == READY) ) {
				send_RTSP_response();
				timer.start();
				estado = PLAYING;
			}else {
				if( (request_type == PAUSE) && (estado == PLAYING) ) {
					send_RTSP_response();
					timer.stop();
					estado = READY;
				}else {
					if( request_type == TEARDOWN ) {
						send_RTSP_response();
						timer.stop();
						
						try {
							RTSP_socket.close();
							RTP_socket.close();
							System.exit(0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			}
		}	
	}
	
	
	public int parse_RTSP_request() {
		int request_type = -1;
		String requestLine = "";
		try {
			requestLine = dis.readUTF();
			System.out.println(requestLine);
			
			String[] tokens = requestLine.split(":");
			
			if( (tokens[0]).compareTo("SETUP") == 0 ) request_type = SETUP;
			else {
				if( (tokens[0]).compareTo("PLAY") == 0 ) request_type = PLAY;
				else {
					if( (tokens[0]).compareTo("PAUSE") == 0 ) request_type = PAUSE;
					else {
						if( (tokens[0]).compareTo("TEARDOWN") == 0 ) request_type = TEARDOWN;
					}
				}
			}
			
			if (request_type == SETUP) {
				// 0 : request_type
				// 1 : nombre_video
				// 2 : length_video
				// 3 : PORT UDP
		        VIDEO_NAME = tokens[1];
		        VIDEO_LENGHT = Integer.parseInt(tokens[2]);
		        RTP_port = Integer.parseInt(tokens[3]);
		     }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return request_type;
	}
	
	public void send_RTSP_response() {
		try {
			dos.writeUTF("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
