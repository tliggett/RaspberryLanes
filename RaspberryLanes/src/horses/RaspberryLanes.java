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
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Canvas;
import javax.swing.JInternalFrame;

public class RaspberryLanes {

	private JFrame frame;
	private static JTextArea txtPreviousWeek;
	static Player player;
	static ArrayList<ArrayList<String>> horsenames;
	static HorseList stable;
	static BetList betList;
	private JTextField txtCashOnBet;
	private final Action saveGame = new SwingAction();
	private final Action newGame = new SwingAction();
	Image img = null;
	Color[] curColor = { new Color(-3407872), new Color(-403660), new Color(-12865360) };
	static Color[] winColor = { new Color(-3407872), new Color(-403660), new Color(-12865360) };
	private Timer timer;
	static int i;

	static PaintRace canvas;
	static JCheckBox chckbxWatchRace;
	static JComboBox<String> comboBox;
	static JTextArea txtrSgg;
	static JLabel label_3;
	static JLabel lblTime;
	static JLabel lblPlayerName;
	static JLabel winnerName;
	static ImagePanel panel;

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
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public RaspberryLanes() throws NumberFormatException, IOException {

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
		Color darkNavy = new Color(0, 0, 120);
		Color track = new Color(255, 208, 115);
		frame.getContentPane().setBackground(darkNavy);
		frame.getContentPane().setForeground(new Color(255, 20, 147));
		frame.setBounds(0, 0, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtPreviousWeek = new JTextArea();
		txtPreviousWeek.setFont(new Font("Impact", Font.PLAIN, 20));
		txtPreviousWeek.setEditable(false);
		txtPreviousWeek.setForeground(new Color(220, 20, 60));
		txtPreviousWeek.setBackground(darkNavy);
		txtPreviousWeek.setBounds(0, 423, 248, 339);
		frame.getContentPane().add(txtPreviousWeek);
		txtPreviousWeek.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setText("");
		textArea.setForeground(new Color(220, 20, 60));
		textArea.setFont(new Font("Impact", Font.PLAIN, 18));
		textArea.setBackground(new Color(0, 0, 120));
		textArea.setBounds(1008, 450, 166, 211);
		frame.getContentPane().add(textArea);

		JLabel lblRaspberryLanes = new JLabel("RASPBERRY LANES");
		lblRaspberryLanes.setForeground(new Color(220, 20, 60));
		lblRaspberryLanes.setFont(new Font("Impact", Font.PLAIN, 31));
		lblRaspberryLanes.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRaspberryLanes.setBounds(34, 21, 237, 46);
		frame.getContentPane().add(lblRaspberryLanes);

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
		mntmSaveGame.setAction(saveGame);
		mnFile.add(mntmSaveGame);

		canvas = new PaintRace(stable);
		canvas.setBounds(254, 123, 720, 450);
		frame.getContentPane().add(canvas);

		JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(new Color(255, 215, 0));
		label_1.setFont(new Font("Impact", Font.PLAIN, 43));
		label_1.setBackground(new Color(255, 215, 0));
		label_1.setBounds(1050, 364, 100, 56);
		frame.getContentPane().add(label_1);

		winnerName = new JLabel("");
		winnerName.setHorizontalAlignment(SwingConstants.CENTER);
		winnerName.setFont(new Font("Impact", Font.PLAIN, 29));
		winnerName.setBounds(0, 127, 220, 35);
		frame.getContentPane().add(winnerName);

		JLabel labelAge = new JLabel("AGE");
		labelAge.setHorizontalAlignment(SwingConstants.CENTER);
		labelAge.setForeground(new Color(255, 215, 0));
		labelAge.setFont(new Font("Dialog", Font.PLAIN, 13));
		labelAge.setAlignmentX(0.5f);
		labelAge.setBounds(1050, 228, 100, 16);
		frame.getContentPane().add(labelAge);

		ImagePanel selectedHorse = new ImagePanel("src/horses/LogoN.png");
		selectedHorse.setBackground(darkNavy);
		selectedHorse.setBounds(1050, 253, 100, 100);
		frame.getContentPane().add(selectedHorse);
		selectedHorse.image = stable.racers.get(0).graphic.image;
		selectedHorse.repaint();
		curColor[0] = stable.racers.get(0).furColor;
		curColor[1] = stable.racers.get(0).maneColor;
		curColor[2] = stable.racers.get(0).saddleColor;
		label_1.setText("" + stable.getHorseOdds(stable.racers.get(0).name));
		labelAge.setText("AGE " + stable.racers.get(0).age);
		textArea.setText(stable.racers.get(0).toPreview());

		comboBox = new JComboBox<String>();
		comboBox.setForeground(darkNavy);
		comboBox.setBackground(new Color(255, 250, 205));
		comboBox.setBounds(1030, 121, 140, 27);
		comboBox.setMaximumRowCount(10);
		for (Horse racer : stable.racers) {
			comboBox.addItem(racer.name);
		}

		comboBox.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getItemCount() > 0) {
					String horseName = comboBox.getSelectedItem().toString();
					selectedHorse.image = stable.racers.get(comboBox.getSelectedIndex()).graphic.image;
					selectedHorse.repaint();
					label_1.setText("" + stable.getHorseOdds(horseName));
					labelAge.setText("AGE " + stable.racers.get(comboBox.getSelectedIndex()).age);
					textArea.setText(stable.racers.get(comboBox.getSelectedIndex()).toPreview());
				}
			}
		});
		frame.getContentPane().add(comboBox);

		JLabel lblHorse = new JLabel("HORSE");
		lblHorse.setHorizontalAlignment(SwingConstants.CENTER);
		lblHorse.setFont(new Font("Impact", Font.PLAIN, 15));
		lblHorse.setForeground(new Color(220, 20, 60));
		lblHorse.setBounds(1040, 104, 120, 16);
		frame.getContentPane().add(lblHorse);

		JLabel lblOdds = new JLabel("ODDS");
		lblOdds.setHorizontalAlignment(SwingConstants.CENTER);
		lblOdds.setFont(new Font("Wide Latin", Font.PLAIN, 13));
		lblOdds.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblOdds.setForeground(new Color(255, 215, 0));
		lblOdds.setBounds(1050, 423, 100, 16);
		frame.getContentPane().add(lblOdds);

		txtCashOnBet = new JTextField();
		txtCashOnBet.setBackground(new Color(255, 255, 224));
		txtCashOnBet.setForeground(new Color(0, 0, 139));
		txtCashOnBet.setBounds(1050, 160, 100, 26);
		frame.getContentPane().add(txtCashOnBet);
		txtCashOnBet.setColumns(10);

		panel = new ImagePanel("src/horses/LogoN.png");
		panel.setBackground(darkNavy);
		panel.setBounds(0, 159, 220, 203);
		frame.getContentPane().add(panel);

		txtrSgg = new JTextArea();
		txtrSgg.setFont(new Font("Impact", Font.PLAIN, 22));
		txtrSgg.setForeground(new Color(220, 20, 60));
		txtrSgg.setBackground(darkNavy);
		txtrSgg.setText("");
		txtrSgg.setBounds(720, 592, 246, 170);
		frame.getContentPane().add(txtrSgg);

		lblPlayerName = new JLabel(player.toString());
		lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerName.setFont(new Font("Impact", Font.PLAIN, 28));
		lblPlayerName.setForeground(new Color(255, 215, 0));
		lblPlayerName.setBounds(868, 17, 316, 56);
		frame.getContentPane().add(lblPlayerName);

		JLabel label_2 = new JLabel("BET");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(new Color(220, 20, 60));
		label_2.setFont(new Font("Impact", Font.PLAIN, 15));
		label_2.setBounds(1040, 146, 120, 16);
		frame.getContentPane().add(label_2);

		label_3 = new JLabel("");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(new Color(220, 20, 60));
		label_3.setFont(new Font("Impact", Font.PLAIN, 18));
		label_3.setBounds(0, 102, 220, 16);
		frame.getContentPane().add(label_3);

		lblTime = new JLabel("");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Impact", Font.PLAIN, 25));
		lblTime.setBounds(0, 373, 220, 22);
		frame.getContentPane().add(lblTime);

		JButton btnRaceHorses = new JButton("RACE HORSES!!!");
		btnRaceHorses.setBackground(new Color(255, 215, 0));
		btnRaceHorses.setForeground(darkNavy);
		btnRaceHorses.addActionListener(new ActionListener() {
			@SuppressWarnings("null")

			public void actionPerformed(ActionEvent e) {
				stable.doRace();
				canvas.updateStable(stable);
				canvas.startTimer();

			}

		});

		btnRaceHorses.setBounds(1008, 672, 166, 68);
		frame.getContentPane().add(btnRaceHorses);

		JButton btnBetOnHorse = new JButton("Place Bet");
		btnBetOnHorse.setBounds(1050, 194, 99, 23);
		frame.getContentPane().add(btnBetOnHorse);

		btnBetOnHorse.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent e) {
				if (txtCashOnBet.getText().equals(" ")) {
					return;
				}
				String money = txtCashOnBet.getText();
				int cash = Integer.parseInt(money);
				Bet bet = new Bet(comboBox.getSelectedItem().toString(), player.placeBet(cash), stable);
				betList.bets.add(bet);
				txtrSgg.setText(betList.toString());
				lblPlayerName.setText(player.toString());

			}
		});

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

	public static void updateStuff() throws NullPointerException, IOException {
		Collections.sort(stable.racers);
		canvas.updateStable(stable);
		txtPreviousWeek.setText(stable.printResults(1));
		while (stable.racers.size() < 10) {
			int rand = (int) (Math.random() * (horsenames.size() - 1));
			stable.racers.add(new Horse(horsenames.get(rand).get(0)));
		}
		txtrSgg.setText("");
		player.cash += betList.reward(stable);
		lblPlayerName.setText(player.toString());
		comboBox.removeAllItems();
		for (Horse racer : stable.racers) {
			comboBox.addItem(racer.name);
		}
		label_3.setText("WINNER");
		label_3.setForeground(stable.racers.get(0).furColor);
		lblTime.setText(Tools.timeToString(stable.racers.get(0).getTime()));
		lblTime.setForeground(stable.racers.get(0).furColor);
		winnerName.setText(stable.racers.get(0).name);
		winnerName.setForeground(stable.racers.get(0).furColor);

		panel.image = stable.racers.get(0).graphic.image;
		panel.repaint();
	}
}