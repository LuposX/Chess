package render;

import logic.Board;
import logic.Cell;
import pieces.*;

public class ASCIChess {
	
	public static void drawConsoleChess(Cell[][] boardArray) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < boardArray.length; i++)
		  {
			sb.append("").append(System.lineSeparator());
		    sb.append("----------------------------------------------------------").append(System.lineSeparator());

		      for (int j = 0; j < boardArray[i].length; j++)
		      {
		    	  String player_Color = "";
					 
				 // print out the chess board
				 if (boardArray[i][j].getPiece() instanceof Pawn) {
					// check if the piece is black or white
					 if(boardArray[i][j].getPiece().getIsWhite()) {
						 player_Color = "W";
					 } else {
						 player_Color = "B";
					 }
					 
					 sb.append("| " + "Pa_" + player_Color + " ");
					 
				 } else if (boardArray[i][j].getPiece() instanceof Rook) {
					// check if the piece is black or white
					 if(boardArray[i][j].getPiece().getIsWhite()) {
						 player_Color = "W";
					 } else {
						 player_Color = "B";
					 }
					 
					 sb.append("| " + "Ro_" + player_Color + " ");
					 
				 } else if (boardArray[i][j].getPiece() instanceof Bishop) {
					// check if the piece is black or white
					 if(boardArray[i][j].getPiece().getIsWhite()) {
						 player_Color = "W";
					 } else {
						 player_Color = "B";
					 }
					 
					 sb.append("| " + "Bi_" + player_Color + " ");
					 
				 } else if (boardArray[i][j].getPiece() instanceof Queen) {
					// check if the piece is black or white
					 if(boardArray[i][j].getPiece().getIsWhite()) {
						 player_Color = "W";
					 } else {
						 player_Color = "B";
					 }
					 
					 sb.append("| " + "Qu_" + player_Color + " ");
					 
				 } else if (boardArray[i][j].getPiece() instanceof King) {
					// check if the piece is black or white
					 if(boardArray[i][j].getPiece().getIsWhite()) {
						 player_Color = "W";
					 } else {
						 player_Color = "B";
					 }
					 
					 sb.append("| " + "Ki_" + player_Color + " ");
					 
				 } else if (boardArray[i][j].getPiece() instanceof Knight) {
					// check if the piece is black or white
					 if(boardArray[i][j].getPiece().getIsWhite()) {
						 player_Color = "W";
					 } else {
						 player_Color = "B";
					 }
					 
					 sb.append("| " + "Kn_" + player_Color + " ");
					 
				 } else {
					 sb.append("| " + "     ");
					 
				 }
		      }       
		      sb.append("|").append(System.lineSeparator());
		    }
		sb.append("").append(System.lineSeparator());
	    sb.append("----------------------------------------------------------").append(System.lineSeparator());
		System.out.println(sb.toString());
	}
}
