package logic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import pieces.*;


public class Board {
	private Cell[][] boardArray;
	private int boardSizeX;
	private int boardSizeY;
	private int tileSizeInPx;
	
	/**
	 * @param sizeX	how many the x-axis in tiles should be
	 * @param sizeY	how many the y-axis in tiles should be
	 */
	public Board(int sizeX, int sizeY, int tileSize, boolean useTestBoard) {
		this.boardSizeX = sizeX;
		this.boardSizeY = sizeY;
		this.tileSizeInPx = tileSize;
		this.boardArray = new Cell[sizeX][sizeY];

		if (useTestBoard) {
			testBoard();
		} else {
			resetBoard();
		}
	}

	/**
	 * Update the boardArray with the new chess move.
	 * @param move The new Move with which the boardArray should be updated
	 */
	public void updateBoard(Move move) {
		this.boardArray[move.getStartPoint().y][move.getStartPoint().x] = new Cell(move.getStartPoint().y, move.getStartPoint().x, null);
		this.boardArray[move.getEndPoint().y][move.getEndPoint().x] = new Cell(move.getEndPoint().y, move.getEndPoint().x, move.getPiece());
	}
	
	/**
	 * Getter for boardArray
	 * @return boardArray an array which has cells in it these cells contain the pieces, cordinates, ect.
	 */
	public Cell[][] getBoardArray() {
		return this.boardArray;
	}
	
	/**
	 * Setter for boardArray
	 * @param x	x-coordinate which shall be set
	 * @param y	y-coordinate which shall be set
	 * @param value	the new set value for the array corresponding to the coordinates
	 */
	public void setBoardArray(int x, int y, Cell value) {
		this.boardArray[y][x] = value;
	}
	
	/**
	 * Draws the Pieces on the board from the array
	 * @param g Graphics get it from JPannel
	 */
	public void drawPieces(Graphics g) {
		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray.length; j++) {
				if(boardArray[i][j].getPiece() != null) {
					BufferedImage tmpImg = boardArray[i][j].getPiece().getImage();
					tmpImg = Misc.resize(tmpImg, this.tileSizeInPx, this.tileSizeInPx);
					g.drawImage(tmpImg, boardArray[i][j].getX() * this.tileSizeInPx, boardArray[i][j].getY() * this.tileSizeInPx, null);
				}
			}
		}
	}
	
	/**
	 * Draws a Chess Board.
	 */
	public void drawChessBoard(Graphics g){	
		for (int a = 0; a < this.boardSizeY; a++) {
			for (int b = 0; b < this.boardSizeY; b++) {
				if((a + b) % 2 == 0){
					 g.setColor(new Color(254,206,159,255));
				     g.fillRect(0 + b * this.tileSizeInPx, 0 + a * this.tileSizeInPx, this.tileSizeInPx, this.tileSizeInPx);
				} else {
					 g.setColor( new Color(209,138,70,255));
				     g.fillRect(0 + b * this.tileSizeInPx, 0 + a * this.tileSizeInPx, this.tileSizeInPx, this.tileSizeInPx);
				}
			}
		}
	}
	
	/**
	 * Draws a Chess Board.
	 * @param r1 red part of rgba of chess color 1
	 * @param g1 green part of rgba of chess color 1
	 * @param b1 blue part of rgba of chess color 1
	 * @param r2 red part of rgba of chess color 2
	 * @param g2 green part of rgba of chess color 2
	 * @param b2 blue part of rgba of chess color 2
	 */
	public void drawChessBoard(Graphics g, Color c1, Color c2){
		for (int a = 0; a < this.boardSizeY; a++) {
			for (int b = 0; b <this.boardSizeY; b++) {
				if((a + b) % 2 == 0){
					 g.setColor(c1);
				     g.fillRect(0 + b * this.tileSizeInPx, 0 + a * this.tileSizeInPx, this.tileSizeInPx, this.tileSizeInPx);
				} else {
					g.setColor(c2);
				     g.fillRect(0 + b * this.tileSizeInPx, 0 + a * this.tileSizeInPx, this.tileSizeInPx, this.tileSizeInPx);
				}
			}
		}
	}

	public void testBoard() {
		// Init remaining cells without pieces
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boardArray[i][j] = new Cell(0, 0, null);
			}
		}

		boardArray[4][3] = new Cell(4, 3, new Pawn(true));
		boardArray[7][4] = new Cell(7, 4, new King(true));

		boardArray[4][7] = new Cell(4, 7, new Queen(false));
		boardArray[5][6] = new Cell(5, 6, new Queen(false));
	}


	/*
	 * Resets the logic Chess Board to default setting.
	 */
	public void resetBoard() {
		// Init black pieces for first rank
		boardArray[0][0] = new Cell(0, 0, new Rook(false));
		boardArray[0][1] = new Cell(0, 1, new Knight(false));
		boardArray[0][2] = new Cell(0, 2, new Bishop(false));
		boardArray[0][3] = new Cell(0, 3, new Queen(false));
		boardArray[0][4] = new Cell(0, 4, new King(false));
		boardArray[0][5] = new Cell(0, 5, new Bishop(false));
		boardArray[0][6] = new Cell(0, 6, new Knight(false));
		boardArray[0][7] = new Cell(0, 7, new Rook(false));
		
		// Init black pawns for second rank
		for (int i = 0; i < boardArray[1].length; i++) {
			boardArray[1][i] = new Cell(1, i, new Pawn(false));
		}
		
		// Init white pieces for eight rank
		boardArray[7][0] = new Cell(7, 0, new Rook(true));
		boardArray[7][1] = new Cell(7, 1, new Knight(true));
		boardArray[7][2] = new Cell(7, 2, new Bishop(true));
		boardArray[7][3] = new Cell(7, 3, new Queen(true));
		boardArray[7][4] = new Cell(7, 4, new King(true));
		boardArray[7][5] = new Cell(7, 5, new Bishop(true));
		boardArray[7][6] = new Cell(7, 6, new Knight(true));
		boardArray[7][7] = new Cell(7, 7, new Rook(true));
		
		// Init white pawns for seventh rank
		for (int i = 0; i < boardArray[1].length; i++) {
			boardArray[6][i] = new Cell(6, i, new Pawn(true));
		}
		
		// Init remaining cells without pieces
		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				boardArray[i][j] = new Cell(0, 0, null);;
			}
		}
	}
}
