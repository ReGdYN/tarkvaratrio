package ee.ut.math.tvt.tarkvaratrio;

import java.awt.*;
import javax.swing.*; 

public class IntroUI {
	
	static String kursus = "2.kursus";
	static String nimi = "Tarkvaratrio";
	static String juht = "Taavi Jaanson";
	static String email = "regdyn@gmail.com";
	static String liikmed = "Kristjan-Julius Laak, Henri Molloka";
	
	
	static void createWindow() {
		JFrame raam = new JFrame("IntroUI");
		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//String kursus = "2. kurus";
		//raam.getContentPane().add(kursus);
		//raam.add(new JTextField(kursus));
		
		ImageIcon pilt = new ImageIcon("./img/troll.png");
		JLabel pildiKast = new JLabel(pilt);
		pildiKast.setVisible(true);
		raam.add(pildiKast);
		
		
		raam.setLocationRelativeTo(null); 
		raam.pack();
		raam.setSize(300, 300);
		raam.setVisible(true); 
	}
}
