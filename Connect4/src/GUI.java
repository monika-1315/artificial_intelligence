import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI {

	private ConnectFour game;
	private Board board;
	private int player= 0;
	private JFrame window = new JFrame();
	private JLabel info;
	JButton[] dropButtons;// drop0, drop1, drop2, drop3, drop4, drop5, drop6;
	public GUI() {
		game = new ConnectFour();
		board=game.getBoard();
		dropButtons = new JButton[board.getWidth()];
		createWindow();
		startPlay();
	}
	private void createWindow() {

		JPanel ramka = new JPanel();
		info = new JLabel();
		window.getContentPane().add(BorderLayout.CENTER, ramka);
		window.getContentPane().add(BorderLayout.NORTH, info);
		
		ramka.setLayout(new GridLayout(7,7));
		
		for (int i=0; i<dropButtons.length; i++) {
			dropButtons[i] = new JButton(String.valueOf(i+1)); 
			dropButtons[i].addActionListener(new DropActionListener(i)); 
			ramka.add(dropButtons[i]);
		}
		
		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				ramka.add(new Gap(row,col));
			}
		}	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Connect4");
		info.setText("hello");
		info.setMinimumSize(new Dimension(500,50));
		ramka.setSize(500,500);
		window.setSize(500,550);
		
		window.setVisible(true);
		
	}
	private void startPlay() {

//		ComputerPlayer ai2=new MinMaxPlayer(game.getBoard(),1, 1);
		ComputerPlayer ai2=new RandomPlayer(game.getBoard());

		
		game.play(ai2, this);
	}

	public void setInfo(String text) {
		info.setText(text);
	}
	public void refresh() {
		window.repaint();
	}
	public void setButtonsEnabled(boolean enabled) {
		for (int i=0; i<dropButtons.length; i++) {
			dropButtons[i].setEnabled(enabled);
		}
	}
	
	class Gap extends JPanel{

		int col, row;
		
		
		public Gap(int row, int col) {
			super();
			this.col = col;
			this.row = row;
		}


		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			if (board.getGrid()[row][col]=='.') {
				g.setColor(Color.WHITE);
			} else if (board.getGrid()[row][col]==Board.PLAYERS[0]) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.RED);
			}
			g.fillOval(0, 0, this.getWidth(), this.getHeight());
		}
		
	}
	class DropActionListener implements ActionListener{
		private int column;
		public DropActionListener(int column) {
			super();
			this.column = column;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(board.drop(Board.PLAYERS[player], column)) {
				game.setDroppedTrue();
				window.repaint();
			}
			else {
				info.setText("Column " + (column+1) + " is full.");
			}
			
		}
	}//DropActionListener
}
