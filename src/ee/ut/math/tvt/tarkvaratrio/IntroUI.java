package ee.ut.math.tvt.tarkvaratrio;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.swing.*; 

public class IntroUI {
	
	static String kursus;
	static String nimi;
	static String juht;
	static String email;
	static String liikmed;
	static String version;
	private final static String uusRida = "\n";
	static URL asukoht;
	
	
	static void createWindow() {
		
		JFrame raam = new JFrame("IntroUI");
		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container sisu = raam.getContentPane();
		sisu.setLayout(new GridLayout(3,1));
		JLabel label1;
		JTextArea label2, label3;
		
		InputStream app_path = null; 
		app_path = ClassLoader.getSystemResourceAsStream("application.properties");
		Properties app_props = new Properties(); 
		try {
			app_props.load(app_path);
			kursus = app_props.getProperty("kursus");
			nimi = app_props.getProperty("nimi");
			juht = app_props.getProperty("juht");
			email = app_props.getProperty("email");
			liikmed = app_props.getProperty("liikmed");
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
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
		ImageIcon pilt = new ImageIcon(asukoht);
		
		JTextArea versioon = new JTextArea();
		versioon.setEditable(false);
		
		InputStream ver_path = null; 
		ver_path = ClassLoader.getSystemResourceAsStream("version.properties");
		Properties ver_props = new Properties(); 
		try {
			ver_props.load(ver_path);
			version = ver_props.getProperty("build.number");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		versioon.append(version);
		
		label1 = new JLabel(pilt, JLabel.CENTER);
		label2 = andmed;
		label3 = versioon;
		sisu.add(label2);
		sisu.add(label1);
		sisu.add(label3);
		
		
		raam.setLocationRelativeTo(null); 
		//raam.pack();
		raam.setSize(300, 300);
		raam.setVisible(true); 
		
	}
}
