package render;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import logic.Game;
import main.GUI;

/*
 * ChessField Inherits from JPanel and is the panel
 * where the Chess board is.
 */
public class ChessPannel extends JPanel implements MouseListener{
	private BufferedImage chessImg;
	private Boolean renderImageBool = false;
	private int size_X, size_Y;
	private int tileSize = 80;
	private int boardSizeX = 8;
	private int boardSizeY = 8;

	private GUI gui;
	private Game game;

	
	/**
	 * Setup for drawing a chess board.
	 * @param size_X  How big the Panel is
	 * @param size_Y  How big the Panel is
	 */
	public ChessPannel(int size_X, int size_Y, GUI gui, boolean turnOnConsoleBoard) {
		this.gui = gui;
		this.renderImageBool = false;
		this.size_X = size_X;
		this.size_Y = size_Y;
		
		// "Turns on" mouse listener
		this.addMouseListener(this);
		
		this.game = new Game(boardSizeX, boardSizeY, this.tileSize, gui, turnOnConsoleBoard, false); // Init the game
		
	    setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public Game getGame() {
		return this.game;
	}

	/**
	 * Sets the Size of the JPannel.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(this.size_X * this.tileSize, size_Y * this.tileSize);
	}
	
	/**
	 * What is drawn on the JPannel
	 */
	@Override
	public void paintComponent(Graphics g) {	     
		this.game.render(g);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		this.game.makeMove(e, this);	
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {	}
}
