package render;

import main.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel implements ActionListener, ItemListener {
	public JLabel playerTurnLabel;
	private JButton newGame;
	private GUI gui;
	private Choice choiceColor;
	private Choice choiceScenario;

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

		this.choiceColor = new Choice();
		this.choiceColor.setPreferredSize(new Dimension(200, 100));
		this.choiceColor.add("Default");
		this.choiceColor.add("Green");
		this.choiceColor.add("Dark Wood");
		this.choiceColor.add("Icy Sea");
		this.choiceColor.add("Orange");
		this.choiceColor.add("Tournemant");
		this.choiceColor.addItemListener(this);

		this.choiceScenario = new Choice();
		this.choiceScenario.add("Default");
		this.choiceScenario.add("2 Rooks and 1 King");
		this.choiceScenario.addItemListener(this);

		add(this.choiceScenario);
		add(this.choiceColor);
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
		if(e.getSource() == this.newGame) {
			System.out.println("Game reset");
			this.gui.getChessfield().getGame().newGame();

		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == this.choiceColor) {
			int tmpIndex = this.choiceColor.getSelectedIndex();
			System.out.println("Chosen Color Scheme: " + this.choiceColor.getSelectedItem());

			if(tmpIndex == 0) {
				this.gui.getChessfield().getGame().setChessFieldColorScheme(new Color(254,206,159,255), new Color(209,138,70,255));
				this.gui.getChessfield().repaint();

			} else if(tmpIndex == 1) {
				this.gui.getChessfield().getGame().setChessFieldColorScheme(new Color(239,239,210,255), new Color(119,151,86,255));
				this.gui.getChessfield().repaint();

			} else if(tmpIndex == 2) {
				this.gui.getChessfield().getGame().setChessFieldColorScheme(new Color(199,159,110,255), new Color(136,95,65,255));
				this.gui.getChessfield().repaint();

			} else if(tmpIndex == 3) {
				this.gui.getChessfield().getGame().setChessFieldColorScheme(new Color(212,225,228,255), new Color(121,157,176,255));
				this.gui.getChessfield().repaint();

			} else if(tmpIndex == 4) {
				this.gui.getChessfield().getGame().setChessFieldColorScheme( new Color(253,228,179,255), new Color(208,138,25,255));
				this.gui.getChessfield().repaint();

			} else if(tmpIndex == 5) {
				this.gui.getChessfield().getGame().setChessFieldColorScheme(new Color(226,227,220,255), new Color(49,103,72,255));
				this.gui.getChessfield().repaint();

			}

		} else if(e.getSource() == this.choiceScenario) {
			int tmpIndex = this.choiceScenario.getSelectedIndex();
			System.out.println("Chosen Scenario: " + this.choiceScenario.getSelectedItem());

			if(tmpIndex == 0) {
				this.gui.getChessfield().getGame().setUseTestBoard(false);
				this.gui.getChessfield().repaint();

			} else if(tmpIndex == 1) {
				this.gui.getChessfield().getGame().setUseTestBoard(true);
				this.gui.getChessfield().repaint();

			}

		}
	}
}
