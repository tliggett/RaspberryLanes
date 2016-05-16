package horses;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
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
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class RaspberryLanes {

	private JFrame frame;
	private JTextArea txtRaceHorses;
	private JTextArea txtPreviousWeek;
	Player player;
	ArrayList<ArrayList<String>> horsenames;
	HorseList stable;
	BetList betList;
	private JTextField txtCashOnBet;
	private final Action action = new SwingAction();
	Image img = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RaspberryLanes window = new RaspberryLanes();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 * @throws NumberFormatException 
	 */
	public RaspberryLanes() throws NumberFormatException, FileNotFoundException {
		
		horsenames = ReadFile.readfile("src/horses/HorseNameDatabase.txt");
		stable = new HorseList();
		betList = new BetList();
		player = new Player();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 128));
		frame.getContentPane().setForeground(new Color(255, 20, 147));
		frame.setBounds(0, 0, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtRaceHorses = new JTextArea();
		txtRaceHorses.setEditable(false);
		txtRaceHorses.setForeground(new Color(220, 20, 60));
		txtRaceHorses.setBackground(new Color(0, 0, 128));
		txtRaceHorses.setText(stable.toPreview());
		txtRaceHorses.setBounds(301, 439, 274, 339);
		frame.getContentPane().add(txtRaceHorses);
		txtRaceHorses.setColumns(10);
		
		JCheckBox chckbxWatchRace = new JCheckBox("WATCH RACE");
		chckbxWatchRace.setForeground(new Color(220, 20, 60));
		chckbxWatchRace.setBounds(1008, 597, 166, 33);
		frame.getContentPane().add(chckbxWatchRace);
		
		txtPreviousWeek = new JTextArea();
		txtPreviousWeek.setEditable(false);
		txtPreviousWeek.setForeground(new Color(220, 20, 60));
		txtPreviousWeek.setBackground(new Color(0, 0, 128));
		txtPreviousWeek.setText("Previous Week");
		txtPreviousWeek.setBounds(10, 433, 274, 339);
		frame.getContentPane().add(txtPreviousWeek);
		txtPreviousWeek.setColumns(10);
		
		JLabel lblResults = new JLabel("RESULTS");
		lblResults.setForeground(new Color(220, 20, 60));
		lblResults.setFont(new Font("Impact", Font.PLAIN, 18));
		lblResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblResults.setBounds(10, 405, 274, 16);
		frame.getContentPane().add(lblResults);
		
		JLabel lblRaspberryLanes = new JLabel("RASPBERRY LANES");
		lblRaspberryLanes.setForeground(new Color(220, 20, 60));
		lblRaspberryLanes.setFont(new Font("Impact", Font.PLAIN, 31));
		lblRaspberryLanes.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRaspberryLanes.setBounds(34, 21, 237, 46);
		frame.getContentPane().add(lblRaspberryLanes);
		
		JLabel label = new JLabel("THIS WEEK");
		label.setForeground(new Color(220, 20, 60));
		label.setFont(new Font("Impact", Font.PLAIN, 18));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(296, 405, 274, 16);
		frame.getContentPane().add(label);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 215, 0));
		menuBar.setBounds(0, 0, 1200, 22);
		frame.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("FILE");
		mnFile.setBackground(new Color(255, 215, 0));
		menuBar.add(mnFile);
		
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.setBackground(new Color(255, 255, 255));
		mnFile.add(mntmNewGame);
		
		JMenuItem mntmSaveGame = new JMenuItem("Save Game");
		mntmSaveGame.setAction(action);
		mnFile.add(mntmSaveGame);
		
		JMenu mnFeatures = new JMenu("Features");
		mnFeatures.setBackground(new Color(255, 215, 0));
		menuBar.add(mnFeatures);
		
		JMenuItem mntmBetting = new JMenuItem("Betting");
		mnFeatures.add(mntmBetting);
		
		
		JMenuItem mntmStatistics = new JMenuItem("Statistics");
		mnFeatures.add(mntmStatistics);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setForeground(new Color(0, 0, 128));
		comboBox.setBackground(new Color(255, 250, 205));
		comboBox.setBounds(626, 228, 237, 27);
		comboBox.setMaximumRowCount(17);
		for(Horse racer : stable.racers){
			comboBox.addItem(racer.name);
		}
		frame.getContentPane().add(comboBox);
		
		JLabel lblHorse = new JLabel("HORSE");
		lblHorse.setForeground(new Color(220, 20, 60));
		lblHorse.setBounds(626, 212, 147, 16);
		frame.getContentPane().add(lblHorse);
		
		JLabel lblOdds = new JLabel("ODDS");
		lblOdds.setFont(new Font("Wide Latin", Font.PLAIN, 13));
		lblOdds.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblOdds.setForeground(new Color(255, 215, 0));
		lblOdds.setBounds(985, 179, 86, 16);
		frame.getContentPane().add(lblOdds);
		
		JLabel label_1 = new JLabel("1:1");
		label_1.setForeground(new Color(255, 215, 0));
		label_1.setFont(new Font("Impact", Font.PLAIN, 43));
		label_1.setBackground(new Color(255, 215, 0));
		label_1.setBounds(990, 231, 73, 89);
		frame.getContentPane().add(label_1);
		
		txtCashOnBet = new JTextField();
		txtCashOnBet.setBackground(new Color(255, 255, 224));
		txtCashOnBet.setForeground(new Color(0, 0, 139));
		txtCashOnBet.setBounds(626, 262, 237, 26);
		frame.getContentPane().add(txtCashOnBet);
		txtCashOnBet.setColumns(10);
		
		ImagePanel panel = new ImagePanel();
		panel.setBackground(new Color(0, 0, 128));
		panel.setBounds(60, 79, 274, 277);
		frame.getContentPane().add(panel);
		
		
		JTextArea txtrSgg = new JTextArea();
		txtrSgg.setForeground(new Color(220, 20, 60));
		txtrSgg.setBackground(new Color(0, 0, 128));
		txtrSgg.setText("");
		txtrSgg.setBounds(626, 314, 222, 193);
		frame.getContentPane().add(txtrSgg);
		
		JLabel lblPlayerName = new JLabel(player.toString());
		lblPlayerName.setFont(new Font("Impact", Font.PLAIN, 28));
		lblPlayerName.setForeground(new Color(255, 215, 0));
		lblPlayerName.setBounds(862, 35, 312, 56);
		frame.getContentPane().add(lblPlayerName);
		
		JButton btnBetOnHorse = new JButton("Bet on Horse!");
		btnBetOnHorse.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent e) {
				String money = txtCashOnBet.getText();
				int cash = Integer.parseInt(money);
				Bet bet = new Bet(comboBox.getSelectedItem().toString(), player.placeBet(cash), 0.5);
				betList.bets.add(bet);
				txtrSgg.setText(betList.toString());
				lblPlayerName.setText(player.toString());
				
			}
		});
		
		btnBetOnHorse.setBounds(954, 341, 117, 29);
		frame.getContentPane().add(btnBetOnHorse);
		
		JButton btnRaceHorses = new JButton("RACE HORSES!!!");
		btnRaceHorses.setBackground(new Color(255, 215, 0));
		btnRaceHorses.setForeground(new Color(0, 0, 128));
		btnRaceHorses.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void updateStuff() throws NullPointerException{
				txtPreviousWeek.setText(stable.toString());
				while(stable.racers.size() < 20){
					int rand = (int)(Math.random()*(horsenames.size()-1));
					stable.racers.add(new Horse(horsenames.get(rand).get(0)));	
				}
				
				txtRaceHorses.setText(stable.toPreview());
				txtrSgg.setText("");
				player.cash += betList.reward(stable);
				lblPlayerName.setText(player.toString());
				comboBox.removeAllItems();
				for(Horse racer : stable.racers){
					comboBox.addItem(racer.name);
				}
			}
			
			public void actionPerformed(ActionEvent e) {
				stable.doRace();
				updateStuff();	
			}
				
		
			
			
		});
		
		btnRaceHorses.setBounds(1008, 630, 166, 68);
		frame.getContentPane().add(btnRaceHorses);
		
		
		
		
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Save Game");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			try {
				stable.saveToFile();
				player.saveToFile();
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}

}
