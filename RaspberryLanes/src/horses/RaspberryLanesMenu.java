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

public class RaspberryLanesMenu {

	private JFrame frame;
	private static JTextArea txtPreviousWeek;
	static Player player;
	static ArrayList<ArrayList<String>> horsenames;
	static HorseList stable;
	static BetList betList;
	private JTextField txtCashOnBet;
	
	Image img = null;
	static int i;
	static Sound theme;
	Sound trumpet;
	static PaintMenu animation;
	static JCheckBox chckbxWatchRace;
	static JComboBox<String> comboBox;
	static JLabel label_3;
	static JLabel lblTime;
	static JLabel winnerName;
	static ImagePanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RaspberryLanesMenu window = new RaspberryLanesMenu();
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
	public RaspberryLanesMenu() throws LineUnavailableException, Exception {

	
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
		Color darkNavy = new Color(0, 0, 120);
		Color track = new Color(255, 208, 115);
		frame.getContentPane().setBackground(darkNavy);
		frame.getContentPane().setForeground(new Color(255, 20, 147));
		frame.setBounds(0, 0, 1440, 860);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
       

		theme = new Sound("src/horses/theme.wav", 0, -1);
		theme.start();
        

		
		JLabel lblRaspberryLanes = new JLabel("RASPBERRY LANES");
		lblRaspberryLanes.setHorizontalAlignment(SwingConstants.CENTER);
		lblRaspberryLanes.setForeground(new Color(220, 20, 60));
		lblRaspberryLanes.setFont(new Font("Impact", Font.PLAIN, 62));
		lblRaspberryLanes.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRaspberryLanes.setBounds(0, 51, 1423, 170);
		frame.getContentPane().add(lblRaspberryLanes);

		animation = new PaintMenu();
		animation.setBounds(0,0,1440,860);
		frame.getContentPane().add(animation);

		

	}

	

	
	

}