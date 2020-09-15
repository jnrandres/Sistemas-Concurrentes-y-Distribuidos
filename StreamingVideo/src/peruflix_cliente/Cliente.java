package peruflix_cliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Cliente {
	
	String[] video_names = { "bw", "gl", "mm", "ml"};
	String[] video_lenght = {"4519" , "4734", "4916", "4299"};
	
	int video_seleccionado;
	
	// Variables para la GUI
	JFrame f = new JFrame("Cliente");
	JPanel mainPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JLabel iconLabel = new JLabel();
	
	JButton setupButton = new JButton("O");
	JButton playButton = new JButton(">");
	JButton pauseButton = new JButton("||");
	JButton tearButton = new JButton("*");
	
	ImageIcon icon;
	Timer timer;
	
	// Conexion UDP para transmision de video
	DatagramSocket RTP_socket;
	DatagramPacket RTP_rcvdp;
	static int RTP_port = 1234;
	byte[] buffer = new byte[15000];
	ByteArrayInputStream bis;
	BufferedImage bImage;
	
	
	// Variables RTSP conexion TCP 
	static int INIT = 0;
	static int READY = 1;
	static int PLAYING = 2;
	int estado;
	
	Socket RTSP_socket;
	DataInputStream dis;
	DataOutputStream dos;
	static int RTSP_port = 8554;
	
	public Cliente() throws SocketException {
		
		// Construyendo la GUI
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// Configuracion de los botones
		buttonPanel.setLayout(new GridLayout(0,1));
		buttonPanel.add(setupButton);
		buttonPanel.add(playButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(tearButton);
		
		setupButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Presionado el boton de setup");
				if( estado == INIT ) {
					
					// Estableciendo conexion RTP con el servidor
					try {
						
						RTP_socket = new DatagramSocket(RTP_port);
						
						send_RTSP_request("SETUP", video_seleccionado);
						
						// Verificando que llego todo correcto
						if( !parse_server_response().equalsIgnoreCase("ok") ) {
							System.out.println("Problemas con el servidor");
						}else {
							estado = READY;
							System.out.println("Nuevo estado: READY");
						}
						
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			}
		});
		
		playButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Boton de play presionado");
				if( estado == READY ) {
					send_RTSP_request("PLAY");
					
					// Verificando que llego todo correcto
					if( !parse_server_response().equalsIgnoreCase("ok") ) {
						System.out.println("Problemas con el servidor");
					}else {
						estado = PLAYING;
						System.out.println("Nuevo estado: PLAYING");
						timer.start();
					}
				}
			}
		} );
		
		pauseButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Boton de play presionado");
				if( estado == PLAYING ) {
					send_RTSP_request("PAUSE");
					
					// Verificando que llego todo correcto
					if( !parse_server_response().equalsIgnoreCase("ok") ) {
						System.out.println("Problemas con el servidor");
					}else {
						estado = READY;
						System.out.println("Nuevo estado: PAUSE");
						timer.stop();
					}
				}
			}
		} );
		
		
		tearButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Boton de stop presionado");
				send_RTSP_request("TEARDOWN");
				
				// Verificando que llego todo correcto
				if( !parse_server_response().equalsIgnoreCase("ok") ) {
					System.out.println("Problemas con el servidor");
				}else {
					System.out.println("Nuevo estado: STOP");
					timer.stop();
					System.exit(0);
				}
			}
			
		} );
		
		
		
		
		iconLabel.setIcon(null);
		
		mainPanel.setLayout(null);
		mainPanel.add(iconLabel);
		mainPanel.add(buttonPanel);
		mainPanel.setBackground(Color.RED);
		
		//Donde se muestran los frames
		iconLabel.setBounds(0,0,320,240);
		buttonPanel.setBounds(320,0,80,240);
		
		f.getContentPane().add(mainPanel, BorderLayout.CENTER);
		f.setSize(new Dimension(416,278));
		f.setResizable(false);
		f.setVisible(true);
		
		timer = new Timer(20, new delayListener() );
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		
		
	}
	
	private void send_RTSP_request(String request_type) {
		try {
			String salida = request_type + ":" + // ACCION A ENVIAR
							"mm" + ":" + 		 // NOMBRE DEL VIDEO
							"4917";				 // LENGTH DEL VIDEO
			
			dos.writeUTF(salida);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void send_RTSP_request(String request_type, int i) {
		try {
			String salida = request_type + ":" + 		// ACCION A ENVIAR
							video_names[i] + ":" + 	 	// NOMBRE DEL VIDEO
							video_lenght[i] + ":" +		// LENGTH DEL VIDEO
							RTP_port;					// PORT UDP
			
			dos.writeUTF(salida);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String parse_server_response() {
		String statusLine = "error";
		try {
			
			statusLine = dis.readUTF();
			System.out.println(statusLine);
			
		} catch (Exception ex) {
			System.out.println("Exception caught: " + ex);
			System.exit(0);
		}

		return (statusLine);
	}
	
	class delayListener implements ActionListener{

		public void actionPerformed( ActionEvent e ) {
			
			//System.out.println("Esperando los bytes de la imagen");
			RTP_rcvdp = new DatagramPacket(buffer, buffer.length);
			try {
				
				RTP_socket.receive(RTP_rcvdp);
				bis = new ByteArrayInputStream(buffer);
				bImage = ImageIO.read(bis);
				icon = new ImageIcon(bImage);
				iconLabel.setIcon(icon);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
