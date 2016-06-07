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
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	static int phaser;
	Image img = null;
	static int i;
	static JButton btnNewGame;
	static Sound theme;
	Sound trumpet;
	static PaintMenu animation;
	static JCheckBox chckbxWatchRace;
	static JComboBox<String> comboBox;
	static JLabel label_3;
	static JLabel lblTime;
	static JLabel winnerName;
	static ImagePanel panel;
	private JTextField textField;
	private JLabel label;
	private JLabel lblRaspberryLanes;

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
	public RaspberryLanesMenu()  throws LineUnavailableException, Exception {

	
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

		frame.getContentPane().setForeground(new Color(255, 20, 147));
		frame.setBounds(0, 0, 900, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(new Color(0, 0,120));

		theme = new Sound("src/data/theme.wav", 0, -1);
		theme.start();
        
		phaser = 0;
		animation = new PaintMenu();
		animation.setBounds(225,133,450,400);
		frame.getContentPane().add(animation);
		
		
		
		textField = new JTextField();
		textField.setBounds(390, 565, 209, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox<String>();
		
		comboBox.setBackground(new Color(255, 250, 205));
		comboBox.setBounds(388, 603, 212, 27);
		comboBox.setMaximumRowCount(10);
		comboBox.addItem("Classic");
		comboBox.addItem("Christmas");
		comboBox.addItem("Beach");
		comboBox.addItem("Chocolate");

		comboBox.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent e) {
				String str = comboBox.getSelectedItem().toString();
				switch(str){
				case "Chocolate":
					animation.setImage("src/data/chocolatemap.png");
					break;
				case "Classic":
					animation.setImage("src/data/Track.png");
					break;
				case "Christmas":
					animation.setImage("src/data/xmas.png");
					break;
				case "Beach":
					animation.setImage("src/data/beach.png");
					break;
				}
			}
		});
		frame.getContentPane().add(comboBox);
		
		JLabel lblName = new JLabel("NAME");
		lblName.setForeground(new Color(220, 20, 60));
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblName.setBounds(300, 565, 92, 27);
		frame.getContentPane().add(lblName);
		
		label = new JLabel("MAP");
		label.setForeground(new Color(220, 20, 60));
		label.setFont(new Font("Tahoma", Font.PLAIN, 32));
		label.setBounds(300, 603, 92, 27);
		frame.getContentPane().add(label);
		
		lblRaspberryLanes = new JLabel("RASPBERRY LANES");
		lblRaspberryLanes.setHorizontalAlignment(SwingConstants.CENTER);
		lblRaspberryLanes.setForeground(new Color(220, 20, 60));
		lblRaspberryLanes.setFont(new Font("Tahoma", Font.PLAIN, 85));
		lblRaspberryLanes.setBounds(10, 21, 864, 106);
		frame.getContentPane().add(lblRaspberryLanes);
		
		btnNewGame = new JButton("NEW GAME");
		btnNewGame.addActionListener(new ActionListener() {
			@SuppressWarnings("null")

			public void actionPerformed(ActionEvent e) {
				try {
					
					RaspberryLanes.main(true, textField.getText(), comboBox.getSelectedItem().toString());
					frame.dispose();
					theme.stop();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		btnNewGame.setBounds(300, 641, 300, 54);
		frame.getContentPane().add(btnNewGame);
		
		JButton btnLoadGame = new JButton("LOAD GAME");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					RaspberryLanes.main(false, textField.getText(), comboBox.getSelectedItem().toString());
					frame.dispose();
					theme.stop();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLoadGame.setBounds(300, 697, 300, 54);
		frame.getContentPane().add(btnLoadGame);
		
		
		

	}
}
