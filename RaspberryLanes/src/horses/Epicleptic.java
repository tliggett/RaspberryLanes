package horses;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

public class Epicleptic {

	private JFrame frame;
	static Sound theme;
	Sound trumpet;
	static Epic animation;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Epicleptic window = new Epicleptic();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 * @throws LineUnavailableException 
	 */
	public Epicleptic() throws LineUnavailableException, Exception {

	
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 */
	private void initialize() throws LineUnavailableException, IOException, Exception {
		
		//Establishes original frame and sets special colors.
		frame = new JFrame();
		frame.setBounds(0, 0, 1440, 860);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       

		theme = new Sound("src/data/chocolate.wav", 0, -1);
		theme.start();
        

		
		

		animation = new Epic();
		animation.setBounds(0,0,1440,860);
		frame.getContentPane().add(animation);

		

	}

	

	
	

}