package render;

import main.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel implements ActionListener{
	public JLabel playerTurnLabel;
	private JButton newGame;
	private GUI gui;

	public InfoPanel(GUI gui) {
		this.gui = gui;
		setLayout(new GridLayout(4, 1));
		setBorder(BorderFactory.createLineBorder(Color.black));
		setMaximumSize(new Dimension(400, 400));
		
		this.newGame = new JButton("New Game");
		this.newGame.addActionListener(this);
		this.newGame.setPreferredSize(new Dimension(200, 80));
		this.newGame.setFont(new Font("Calibri", Font.BOLD, 20));

		this.playerTurnLabel = new JLabel("It's White turn.");
		this.playerTurnLabel.setFont(new Font("Calibri", Font.BOLD, 30));
		
		add(this.newGame);
		add(this.playerTurnLabel);
		
		setVisible(true);
	}

	/**
	 * Updates the text of he JLabel which says which palyer turn it is
	 * @param playerTurnText the string with what the label gets updatet
	 */
	public void setPlayerTurnText(String playerTurnText) {
		this.playerTurnLabel.setText(playerTurnText);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource());
		if(e.getSource() == newGame) {
			this.gui.getChessfield().getGame().newGame();
		}
	}
	
}
