package ee.ut.math.tvt.tarkvaratrio;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*; 

@SuppressWarnings("unused")
public class IntroUI {
	
	static String kursus = "2.kursus";
	static String nimi = "Tarkvaratrio";
	static String juht = "Taavi Jaanson";
	static String email = "regdyn@gmail.com";
	static String liikmed = "Kristjan-Julius Laak, Henri Molloka";
	private final static String uusRida = "\n";
	static URL asukoht;
	
	static void createWindow() {
		JFrame raam = new JFrame("IntroUI");
		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container sisu = raam.getContentPane();
		sisu.setLayout(new GridLayout(3,1));
		JLabel label1;
		JTextArea label2, label3;
		
		JTextArea andmed = new JTextArea();
		andmed.append(" " + kursus + uusRida);
		andmed.append(" " + nimi + uusRida);
		andmed.append(" " + juht + uusRida);
		andmed.append(" " + email + uusRida);
		andmed.append(" " + liikmed);
		andmed.setEditable(false);
		
		URL s = IntroUI.class.getResource("");
		String fail = s + "img/troll.png";
		try {
			asukoht = new URL(fail);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println(asukoht);
		ImageIcon pilt = new ImageIcon(asukoht);
		
		label1 = new JLabel(pilt, JLabel.CENTER);
		label2 = andmed;
		sisu.add(label2);
		sisu.add(label1);
		
		
		raam.setLocationRelativeTo(null); 
		//raam.pack();
		raam.setSize(300, 300);
		raam.setVisible(true); 
	}
}
