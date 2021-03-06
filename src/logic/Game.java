package logic;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.GUI;
import pieces.Pawn;
import pieces.King;
import pieces.Piece;
import render.ASCIChess;

public class Game {
	private Board board;
	private int boardSizeX;
	private int boardSizeY;
	private int tileSize;
	private ArrayList<Move> historyMoves; // Used so save the coordinates of where the user clicked, used for making a move with a piece
	private Boolean firstClickPerMoveTry = true;
	private Point firstMouseClickCoordinates;
	private Player playerWhite;
	private Player playerBlack;
	private Player playerTurn;
	private GUI gui;
	private boolean turnOnConsoleBoard;
	private boolean useTestBoard;
	private boolean inCheckMate;
	private Color c1;
	private Color c2;

	public Game(int boardSizeX, int boardSizeY, int tileSize, GUI gui, boolean turnOnConsoleBoard, boolean useTestBoard) {
		this.boardSizeX = boardSizeX;
		this.boardSizeY = boardSizeY;
		this.tileSize = tileSize;
		this.historyMoves = new ArrayList<Move>(); 
		this.gui = gui;
		this.turnOnConsoleBoard = turnOnConsoleBoard;
		this.useTestBoard = useTestBoard;
		this.inCheckMate = false;

		this.board = new Board(boardSizeX, boardSizeY, tileSize, useTestBoard);
		this.playerWhite = new Player(true);
		this.playerBlack = new Player(false);

		// the color of the chess field
		this.c1 = new Color(254,206,159,255);
		this.c2 = new Color(209,138,70,255);

		// Init whos turn it is
		if(this.playerWhite.getIsWhiteSide() == true) {
			this.playerTurn = this.playerWhite;
		} else {
			this.playerTurn = this.playerBlack;
		}
	}

	public void setChessFieldColorScheme(Color c1, Color c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	public void setUseTestBoard(boolean useTestBoard) {
		this.useTestBoard = useTestBoard;

		if(useTestBoard) {
			this.board.testBoard();
		} else {
			this.board.resetBoard();
		}
	}

	public void newGame() {
		// Init whos turn it is
		if(this.playerWhite.getIsWhiteSide() == true) {
			this.playerTurn = this.playerWhite;
		} else {
			this.playerTurn = this.playerBlack;
		}

		this.inCheckMate = false;
		this.historyMoves.clear();

		if (this.useTestBoard) {
			this.board.testBoard();
		} else {
			this.board.resetBoard();
		}

		this.gui.getInfoPanel().setPlayerTurnText("It's White turn.");

		this.gui.getChessfield().repaint();
	}


	/**
	 * Draws the chess-board and the the chess pieces on the screen
	 * @param g Graphics in order to draw on JPannel
	 */
	public void render(Graphics g) {
		this.board.drawChessBoard(g, c1, c2);
		this.board.drawPieces(g);

		if(this.inCheckMate) {
			g.setColor(Color.BLUE);
			g.setFont(new Font("TimesRoman", Font.BOLD, 60));
			g.drawString("CHECKMATE", 150, 200);
		}
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
			Piece startPiece = board.getBoardArray()[this.firstMouseClickCoordinates.y][this.firstMouseClickCoordinates.x].getPiece(); // The Piece from where we start
			Piece endPiece = board.getBoardArray()[tmpCellY][tmpCellX].getPiece(); // The Piece where we end up

			// Check if the path is valid
			if(startPiece != null) {
				if (startPiece.isValidPath(this.firstMouseClickCoordinates.x, this.firstMouseClickCoordinates.y, tmpCellX, tmpCellY, board.getBoardArray(), true, startPiece, endPiece, this.playerTurn)) {

					// Check if end cell is empty
					if (endPiece == null) {

						// Check that pawn doesnt move wrong
						if (!(startPiece instanceof Pawn &&
								(((this.firstMouseClickCoordinates.x + 1 == tmpCellX || this.firstMouseClickCoordinates.x - 1 == tmpCellX) && this.firstMouseClickCoordinates.y - 1 == tmpCellY)
										|| (this.firstMouseClickCoordinates.x + 1 == tmpCellX || this.firstMouseClickCoordinates.x - 1 == tmpCellX) && this.firstMouseClickCoordinates.y + 1 == tmpCellY))) {

							// Check that source piece has same color than player
							if (startPiece.getIsWhite() == playerTurn.getIsWhiteSide()) {
								System.out.println("IsValidMove: True, normal move");
								System.out.println("");

								Move move = new Move(true, this.firstMouseClickCoordinates, new Point(tmpCellX, tmpCellY), startPiece); // create a new move
								this.historyMoves.add(move);  // update the history of chess moves of the game
								board.updateBoard(move);  // update the board array
								this.firstMouseClickCoordinates = null;
								this.firstClickPerMoveTry = true; // Used to check if its the first mouse lick in a chess move
								chessPannel.repaint(); // redraws the JPannel

								if (turnOnConsoleBoard) {
									ASCIChess.drawConsoleChess(board.getBoardArray());  // draws the board in the console
								}

								if (this.playerTurn == playerWhite) {
									this.playerTurn = playerBlack;
									this.gui.getInfoPanel().setPlayerTurnText("It's Black turn.");
								} else {
									this.playerTurn = playerWhite;
									this.gui.getInfoPanel().setPlayerTurnText("It's White turn.");
								}

								// Check if in Checkmate
								boolean kingIsInCheck = Misc.getCheckCheck(board.getBoardArray(), playerTurn);
								if (kingIsInCheck) {
									if (Misc.checkCheckMate(board.getBoardArray(), playerTurn)) {
										System.out.println("In Checkmate");
										this.inCheckMate = true;
									}
								}

							} else {
								this.firstClickPerMoveTry = true;
								System.out.println("IsValidMove: False, wrong color piece");
								System.out.println("");
							}

						} else {
							this.firstClickPerMoveTry = true;
							System.out.println("IsValidMove: False, Pawn cant move diagonal");
							System.out.println("");
						}

						// End cell is not empty
					} else {
						this.firstClickPerMoveTry = true;

						// You cant capture the King
						if (!(endPiece instanceof King)) {
							// Pawn are only allowed to capture diagonal
							if (startPiece instanceof Pawn) {

								// Check that the pawn doesnt move straight forward not allowed
								if (!((tmpCellY == this.firstMouseClickCoordinates.y + 1 && tmpCellX == this.firstMouseClickCoordinates.x)
										|| (tmpCellY == this.firstMouseClickCoordinates.y - 1 && tmpCellX == this.firstMouseClickCoordinates.x))) {

									System.out.println("IsValidMove: True, Capture with Pawn");
									System.out.println("");

									Move move = new Move(true, this.firstMouseClickCoordinates, new Point(tmpCellX, tmpCellY), startPiece); // create a new move
									this.historyMoves.add(move);  // update the history of chess moves of the game
									board.updateBoard(move);  // update the board array
									this.firstMouseClickCoordinates = null;
									this.firstClickPerMoveTry = true; // Used to check if its the first mouse lick in a chess move
									chessPannel.repaint(); // redraws the JPannel

									if (turnOnConsoleBoard) {
										ASCIChess.drawConsoleChess(board.getBoardArray());  // draws the board in the console
									}

									if (this.playerTurn == playerWhite) {
										this.playerTurn = playerBlack;
										this.gui.getInfoPanel().setPlayerTurnText("It's Black turn.");
									} else {
										this.playerTurn = playerWhite;
										this.gui.getInfoPanel().setPlayerTurnText("It's White turn.");
									}

									// Check if in Checkmate
									boolean kingIsInCheck = Misc.getCheckCheck(board.getBoardArray(), playerTurn);
									if (kingIsInCheck) {
										if (Misc.checkCheckMate(board.getBoardArray(), playerTurn)) {
											System.out.println("In Checkmate");
											this.inCheckMate = true;
										}
									}


								} else {
									this.firstClickPerMoveTry = true;
									System.out.println("IsValidMove: False, Pawns cant capture straight");
									System.out.println("");
								}

								// Capture with any piece besides pawn
							} else {
								System.out.println("IsValidMove: True, Capture with a Piece");
								System.out.println("");

								Move move = new Move(true, this.firstMouseClickCoordinates, new Point(tmpCellX, tmpCellY), startPiece); // create a new move
								this.historyMoves.add(move);  // update the history of chess moves of the game
								board.updateBoard(move);  // update the board array
								this.firstMouseClickCoordinates = null;
								this.firstClickPerMoveTry = true; // Used to check if its the first mouse lick in a chess move
								chessPannel.repaint(); // redraws the JPannel

								if (turnOnConsoleBoard) {
									ASCIChess.drawConsoleChess(board.getBoardArray());  // draws the board in the console
								}

								// update the text in the infopanel
								if (this.playerTurn == playerWhite) {
									this.playerTurn = playerBlack;
									this.gui.getInfoPanel().setPlayerTurnText("It's Black turn.");
								} else {
									this.playerTurn = playerWhite;
									this.gui.getInfoPanel().setPlayerTurnText("It's White turn.");
								}

								// Check if in Checkmate
								boolean kingIsInCheck = Misc.getCheckCheck(board.getBoardArray(), playerTurn);
								if (kingIsInCheck) {
									if (Misc.checkCheckMate(board.getBoardArray(), playerTurn)) {
										System.out.println("In Checkmate");
										this.inCheckMate = true;
									}
								}

							}
						} else {
							this.firstClickPerMoveTry = true;
							System.out.println("IsValidMove: False, Cant capture king");
							System.out.println("");
						}
					}

				} else {
					this.firstClickPerMoveTry = true;
					System.out.println("IsValidMove: False, not a valid path");
					System.out.println("");
				}
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
