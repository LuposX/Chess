package logic;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.GUI;
import pieces.Pawn;
import pieces.King;
import pieces.Piece;
import render.ASCIChess;
import render.ChessPannel;
import render.InfoPanel;

public class Game {
	private Board board;
	private int boardSizeX;
	private int boardSizeY;
	private int tileSize;
	private ArrayList<Move> historyMoves; // Used so save the coordinates of where the user clicked, used for making a move with a piece
	private Boolean firstClickPerMoveTry = true;
	private Point firstMouseClickCoordinates;
	private Player player1;
	private Player player2;
	private Player playerTurn;
	private GUI gui;
	
	public Game(int boardSizeX, int boardSizeY, int tileSize, GUI gui) {
		this.boardSizeX = boardSizeX;
		this.boardSizeY = boardSizeY;
		this.tileSize = tileSize;
		this.historyMoves = new ArrayList<Move>(); 
		this.gui = gui;
		
		this.board = new Board(boardSizeX, boardSizeY, tileSize);
		this.player1 = new Player(true);
		this.player2 = new Player(false);
		
		// Init whos turn it is
		if(this.player1.getIsWhiteSide() == true) {
			this.playerTurn = this.player1;
		} else {
			this.playerTurn = this.player2;
		}
	}

	public void newGame() {
		// Init whos turn it is
		if(this.player1.getIsWhiteSide() == true) {
			this.playerTurn = this.player1;
		} else {
			this.playerTurn = this.player2;
		}

		this.historyMoves = new ArrayList<Move>();

		this.board.resetBoard();
		this.gui.getChessfield().repaint();
	}

	/**
	 * Draws the chess-board and the the chess pieces on the screen
	 * @param g Graphics in order to draw on JPannel
	 */
	public void render(Graphics g) {
		this.board.drawChessBoard(g);
		this.board.drawPieces(g);
	}
	
	 /*
	 * Makes a new chess Move
	 * @param e MouseEvent from which we can read data like where did the person press in order to find out what piece to move
	 * @param chessPannel  Used for repaint() forcing the JPannel to update
	 */
	public void makeMove(MouseEvent e, JPanel chessPannel) {
		// Transform pixel coordinates to number of tiles
		int tmpCellX = e.getX() / 80;
		int tmpCellY = e.getY() / 80;
		
		System.out.println("Mouse click=X: " + tmpCellX + "   Y: " + tmpCellY);
		
		// Check if this is the first click starting a move
		if(this.firstClickPerMoveTry) {
			// Check if the tile clicked has a piece standing on it
			if(board.getBoardArray()[tmpCellY][tmpCellX].getPiece() != null) {
				
				// Check if its the first mouse click in a chess move
				this.firstClickPerMoveTry = false;
				
				// Saves the coordinate point where user clicked
				this.firstMouseClickCoordinates = new Point(tmpCellX, tmpCellY);	
			} else {
				this.firstClickPerMoveTry = true;
			}
			
		} else {
			// Get the piece from the current cell
			Piece startPiece =  board.getBoardArray()[this.firstMouseClickCoordinates.y][this.firstMouseClickCoordinates.x].getPiece(); // The Piece from where we start
			Piece endPiece =  board.getBoardArray()[tmpCellY][tmpCellX].getPiece(); // The Piece where we end up
			
			// Check if the path is valid
			if (startPiece.isValidPath(this.firstMouseClickCoordinates.x, this.firstMouseClickCoordinates.y, tmpCellX, tmpCellY, board.getBoardArray())) {
				
				// Check if end cell is empty
				if (endPiece == null) {

					// Check that pawn doesnt move wrong
					if(!(startPiece instanceof Pawn  &&
							(((this.firstMouseClickCoordinates.x + 1 == tmpCellX || this.firstMouseClickCoordinates.x - 1 == tmpCellX) && this.firstMouseClickCoordinates.y - 1 == tmpCellY)
							|| (this.firstMouseClickCoordinates.x + 1 == tmpCellX || this.firstMouseClickCoordinates.x - 1 == tmpCellX) && this.firstMouseClickCoordinates.y + 1 == tmpCellY)))
					{

						// Check that source piece has same color than player
						if (startPiece.getIsWhite() == playerTurn.getIsWhiteSide()) {
							System.out.println("IsValidMove: True, normal move");

							Move move = new Move(true, this.firstMouseClickCoordinates, new Point(tmpCellX, tmpCellY), startPiece); // create a new move
							this.historyMoves.add(move);  // update the history of chess moves of the game
							board.updateBoard(move);  // update the board array
							ASCIChess.drawConsoleChess(board.getBoardArray());  // draws the board in the console
							this.firstMouseClickCoordinates = null;
							this.firstClickPerMoveTry = true; // Used to check if its the first mouse lick in a chess move
							chessPannel.repaint(); // redraws the JPannel

							if (this.playerTurn == player1) {
								this.playerTurn = player2;
								this.gui.getInfoPanel().setPlayerTurnText("It's Player 2 turn.");
							} else {
								this.playerTurn = player1;
								this.gui.getInfoPanel().setPlayerTurnText("It's Player 1 turn.");
							}

						} else {
							this.firstClickPerMoveTry = true;
							System.out.println("IsValidMove: False, wrong color piece");
						}

					}  else {
						this.firstClickPerMoveTry = true;
						System.out.println("IsValidMove: False, Pawn cant move diagonal");
					}

				   // End cell is not empty
				}  else {
						this.firstClickPerMoveTry = true;
						// cant capture own piece
						if(startPiece.getIsWhite() != endPiece.getIsWhite()) {


							// You cant capture the King
							if(!(endPiece instanceof King)) {
								// Pawn are only allowed to capture diagonal
								if (startPiece instanceof Pawn) {

									// Check that the pawn doesnt move straight forward not allowed
									if (!((tmpCellY  == this.firstMouseClickCoordinates.y + 1 && tmpCellX  == this.firstMouseClickCoordinates.x)
											|| (tmpCellY == this.firstMouseClickCoordinates.y - 1 && tmpCellX  == this.firstMouseClickCoordinates.x))) {

										System.out.println("IsValidMove: True, Capture with Pawn");

										Move move = new Move(true, this.firstMouseClickCoordinates, new Point(tmpCellX, tmpCellY), startPiece); // create a new move
										this.historyMoves.add(move);  // update the history of chess moves of the game
										board.updateBoard(move);  // update the board array
										ASCIChess.drawConsoleChess(board.getBoardArray());  // draws the board in the console
										this.firstMouseClickCoordinates = null;
										this.firstClickPerMoveTry = true; // Used to check if its the first mouse lick in a chess move
										chessPannel.repaint(); // redraws the JPannel

										if (this.playerTurn == player1) {
											this.playerTurn = player2;
											this.gui.getInfoPanel().setPlayerTurnText("It's Player 2 turn.");
										} else {
											this.playerTurn = player1;
											this.gui.getInfoPanel().setPlayerTurnText("It's Player 1 turn.");
										}

									} else {
										this.firstClickPerMoveTry = true;
										System.out.println("IsValidMove: False, Pawns cant capture straight");
									}

								// Capture with any piece besides pawn
								} else {
									System.out.println("IsValidMove: True, Capture with a Piece");

									Move move = new Move(true, this.firstMouseClickCoordinates, new Point(tmpCellX, tmpCellY), startPiece); // create a new move
									this.historyMoves.add(move);  // update the history of chess moves of the game
									board.updateBoard(move);  // update the board array
									ASCIChess.drawConsoleChess(board.getBoardArray());  // draws the board in the console
									this.firstMouseClickCoordinates = null;
									this.firstClickPerMoveTry = true; // Used to check if its the first mouse lick in a chess move
									chessPannel.repaint(); // redraws the JPannel

									if (this.playerTurn == player1) {
										this.playerTurn = player2;
										this.gui.getInfoPanel().setPlayerTurnText("It's Player 2 turn.");
									} else {
										this.playerTurn = player1;
										this.gui.getInfoPanel().setPlayerTurnText("It's Player 1 turn.");
									}

								}
							} else {
								this.firstClickPerMoveTry = true;
								System.out.println("IsValidMove: False, Cant capture king");
							}

						} else {
							this.firstClickPerMoveTry = true;
							System.out.println("IsValidMove: False, Cant capture own piece");
						}
					
					}
					
				} else {
					this.firstClickPerMoveTry = true;
					System.out.println("IsValidMove: False, not a valid path");
			}
		}
	}
	
	/**
	 * Getter for board
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}
}
