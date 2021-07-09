package main;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import render.ChessPannel;
import render.InfoPanel;

/**
 * Used for displaying the game.
 *
 */
public class GUI extends JFrame implements ActionListener{
	private Container c;
	private Graphics g;
	private ChessPannel chessfieldPannel;
	private InfoPanel infoPanel;

	/**
	 *  Uses the Inherited class JFrame to display the Game.
	 * @param main	is from where the game is started from
	 */
	public GUI(Main main){
		Init("Title", 1400, 900);	
		
		// Boxlayout for buttons on the side
		this.infoPanel = new InfoPanel(this);
		
		// Create the ChessBoard
		 this.chessfieldPannel = new ChessPannel(8, 8, this);
		
		add(chessfieldPannel);
		add(infoPanel);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public ChessPannel getChessfield() {
		return this.chessfieldPannel;
	}

	public InfoPanel getInfoPanel() {
		return this.infoPanel;
	}

	/**
	 * Initalizes the parameetr for the JFrame.
	 * Such as Title, size, ect.
	 * @param 
	 */
	public void Init(String Title, int width, int height) {
		setTitle("Chess");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null); // Makes that the GUI is shown in the middle of the screen
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setResizable(false);
	}
	
	public void sleep() {
		
	}
	
}
