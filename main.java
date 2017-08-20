/*
 * Δημητρης Δημητρακοπουλος-3130053
 * Λεωνιδας Παππας-3130166
 * Αργυρης Ρουστεμης-3130180
 */

import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		Board gameboard = new Board();		
		Board.currentplayer = "X"; // Human
		Score4player ai_player = new Score4player(gameboard);

		while(true){
			gameboard.draw();
			if(Board.currentplayer.equals("X")){
				
				System.out.println("Human turn: ");
				Scanner input = new Scanner(System.in);
				gameboard.setGameboard(input.nextInt(), "X");
			}else{
				System.out.println("Machine turn");
				
				gameboard = new Board(ai_player.minimax(gameboard));
			}
			Board.numberOfturns++;
			if(gameboard.isTerminal()){
				gameboard.draw();
				System.out.println("@winner is :"+gameboard.winner);
				break;
			}
			if(Board.currentplayer.equals("X")){
				Board.currentplayer="O";
			}else{
				Board.currentplayer="X";
			}
		}
	}
		
}
