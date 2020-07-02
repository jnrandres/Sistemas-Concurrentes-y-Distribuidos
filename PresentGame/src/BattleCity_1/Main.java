package BattleCity_1;

import java.awt.Color;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame obj = new JFrame();
		Juego juego = new Juego();
		
		obj.setBounds(10,10,660,630);
		obj.setTitle("Battle city");
		obj.setBackground(Color.gray);
		obj.setResizable(false);
		
		obj.add(juego);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setVisible(true);
		
	}

}
