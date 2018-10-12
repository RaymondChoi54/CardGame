import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		
		int playerNum = 4;
		
		BigTwo game = new BigTwo(playerNum);
		
		while(game.currentTurn() != -1) {
			System.out.println("It is player " + (game.currentTurn() + 1) + "'s turn");
			Card[] hand = game.currentHand();
			Arrays.sort(hand);
			System.out.println(Arrays.toString(hand));
			ArrayList<Card[]> moves = game.possibleMoves();
			for(int i = 0; i < moves.size(); i++) {
				System.out.println(i + " " + Arrays.toString(moves.get(i)));
			}
			game.applyMove(in.nextInt());
			
		}
		
		in.close();
	}

}
