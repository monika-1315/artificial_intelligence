package game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import evaluators.SimpleEvaluator;
import players.AlphaBetaPlayer;
import players.ComputerPlayer;
import players.MinMaxPlayer;
import players.RandomPlayer;

public class GUI {

	private static final String[] playersOptions = new String[] { "Human Player", "Random - Computer Player",
			"MinMax Computer Player", "AlphaBeta Computer Player" };
	private static final String[] evalOptions = new String[] { "Simple (points for win)" };
	private ConnectFour game;
	private volatile Board board;
	private int player = 0;
	private JFrame window = new JFrame();
	private JLabel info;
	private JButton[] dropButtons;// drop0, drop1, drop2, drop3, drop4, drop5, drop6;
	private volatile boolean waitForUser = false;

	public GUI() {
		game = new ConnectFour();
		board = game.getBoard();
		dropButtons = new JButton[board.getWidth()];
	}

	private void createWindow() {

		JPanel panel = new JPanel();
		info = new JLabel();
		window.getContentPane().add(BorderLayout.CENTER, panel);
		window.getContentPane().add(BorderLayout.NORTH, info);

		panel.setLayout(new GridLayout(7, 7));

		for (int i = 0; i < dropButtons.length; i++) {
			dropButtons[i] = new JButton(String.valueOf(i + 1));
			dropButtons[i].addActionListener(new DropActionListener(i));
			panel.add(dropButtons[i]);
		}
		setButtonsEnabled(false);

		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				panel.add(new Gap(row, col));
			}
		}
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Connect4");
		info.setText("hello");
		info.setMinimumSize(new Dimension(500, 50));
		panel.setSize(500, 500);

		window.setSize(500, 550);

	}

	public void play() {
		createWindow();
		startPlay();
	}
	void startPlay() {
		ComputerPlayer ai1 = getPlayer(0);
		ComputerPlayer ai2 = getPlayer(1);

		window.setVisible(true);
		game.play(ai1, ai2, this);
	}

	private ComputerPlayer getPlayer(int i) {
		JFrame dialog = new JFrame();
		JPanel panel = new JPanel();
//		dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
		dialog.setLayout(new FlowLayout());
		dialog.add(new JLabel("Select player " + (i + 1)));

		JComboBox<String> playersList = new JComboBox<>(playersOptions);
		dialog.add(playersList);
		JButton button = new JButton("Select");
		button.addActionListener(new ButtonSelectedListener());
		dialog.add(button);

		dialog.setTitle("Select player");
		dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		dialog.pack();
		dialog.setSize(200, 130);
		dialog.setVisible(true);
		waitForUser = true;
		do {
		} while (waitForUser);

		dialog.setVisible(false);
		switch (playersList.getSelectedIndex()) {
		case (1):
			return new RandomPlayer(board, i);
		case (2):
			return getMinMaxPlayer(i);
		case (3):
			return getAlphaBetaPlayer(i);
		default:
			return null;
		}
	}

	private MinMaxPlayer getMinMaxPlayer(int i) {
		int depth=getDepth();
		return new MinMaxPlayer(board, i, depth, new SimpleEvaluator());
	}
	private AlphaBetaPlayer getAlphaBetaPlayer(int i) {
		int depth=getDepth();
		return new AlphaBetaPlayer(board, i, depth, new SimpleEvaluator());
	}

	private int getDepth() {
		JFrame dialog = new JFrame();
		JPanel panel = new JPanel();
		dialog.setLayout(new FlowLayout());
		dialog.add(new JLabel("Select algorithm max depth:"));

		SpinnerModel model = new SpinnerNumberModel(5, 1, 40, 1);
		JSpinner spinner = new JSpinner(model);
		dialog.add(spinner);
		dialog.add(new JLabel("Select evaluation method:"));
		
		JComboBox<String>evalList = new JComboBox<>(evalOptions);
		dialog.add(evalList);
		
		JButton button = new JButton("Select");
		button.addActionListener(new ButtonSelectedListener());
		dialog.add(button);

		dialog.setTitle("Select player");
		dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dialog.setSize(200, 170);
		dialog.setVisible(true);
		waitForUser = true;
		do {
		} while (waitForUser);

		dialog.setVisible(false);
		return (int) spinner.getValue();
	}
	void setInfo(String text) {
		info.setText(text);
	}

	void refresh() {
		window.repaint();
	}

	void setButtonsEnabled(boolean enabled) {
		for (int i = 0; i < dropButtons.length; i++) {
			dropButtons[i].setEnabled(enabled);
		}
	}

	void setPlayer(int player) {
		this.player = player;
	}

	void onWin(String player) {
		info.setText("Player " + player + " won!");
		int a = JOptionPane.showConfirmDialog(window,  player + " won!\n Do you want to play again?",
				"Game over", JOptionPane.YES_NO_OPTION);
		if (a == JOptionPane.YES_OPTION) {
			game.clearBoard();
			board = game.getBoard();
			refresh();
			startPlay();
		} else {
			window.setVisible(false);
			System.exit(0);
		}
	}

	class Gap extends JPanel {

		int col, row;

		public Gap(int row, int col) {
			super();
			this.col = col;
			this.row = row;
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			if (board.getGrid()[row][col] == '.') {
				g.setColor(Color.WHITE);
			} else if (board.getGrid()[row][col] == Board.PLAYERS[0]) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.RED);
			}
			g.fillOval(0, 0, this.getWidth(), this.getHeight());
		}

	}

	class DropActionListener implements ActionListener {
		private int column;

		public DropActionListener(int column) {
			super();
			this.column = column;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (board.drop(Board.PLAYERS[player], column)) {
				game.setDroppedTrue();
				window.repaint();
//				setButtonsEnabled(false);
			} else {
				info.setText("Column " + (column + 1) + " is full.");
			}

		}
	}// DropActionListener

	class ButtonSelectedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			waitForUser = false;
		}

	}
}
