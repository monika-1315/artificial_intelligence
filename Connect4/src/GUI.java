import java.awt.BorderLayout;
import java.awt.Color;
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
	private char player= 'X';
	JFrame window = new JFrame();
	
	public GUI() {
		game = new ConnectFour();
		board=game.getBoard();
		startPlay();
	}
	private void startPlay() {

		ComputerPlayer ai2=new MinMaxPlayer(game.getBoard(),1, 1);
		

		JPanel ramka = new JPanel();
		JLabel info = new JLabel();
		window.getContentPane().add(BorderLayout.CENTER, ramka);
		window.getContentPane().add(BorderLayout.NORTH, info);
		
		ramka.setLayout(new GridLayout(7,7));
		
		JButton drop0 = new JButton("|"); drop0.addActionListener(new DropActionListener(0)); 
		JButton drop1 = new JButton("|"); drop1.addActionListener(new DropActionListener(1)); 
		JButton drop2 = new JButton("|"); drop2.addActionListener(new DropActionListener(2));
		JButton drop3 = new JButton("|"); drop3.addActionListener(new DropActionListener(3));
		JButton drop4 = new JButton("|"); drop4.addActionListener(new DropActionListener(4));
		JButton drop5 = new JButton("|"); drop5.addActionListener(new DropActionListener(5));
		JButton drop6 = new JButton("|"); drop6.addActionListener(new DropActionListener(6));
		
		ramka.add(drop0);
		ramka.add(drop1);
		ramka.add(drop2);
		ramka.add(drop3);
		ramka.add(drop4);
		ramka.add(drop5);
		ramka.add(drop6);
		
		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				ramka.add(new Gap(row,col));
			}
		}	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Connect4");
		info.setText("hello");
		info.setSize(100,550);
		ramka.setSize(550,550);
		window.setSize(650,550);
		
		window.setVisible(true);
		
		game.play(ai2);
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
			board.drop(player, column);
			window.repaint();
		}
	}//DropActionListener
}
