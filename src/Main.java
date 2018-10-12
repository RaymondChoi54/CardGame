import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		
		int playerNum = 4;
		BigTwo game = new BigTwo(playerNum);
		
		while(game.currentTurn() != -1) {
			System.out.println(game.gameView());
			if(game.possibleMoves().size() > 0) {
				game.applyMove(in.nextInt());
			} else {
				game.applyMove(-1);
			}
		}
		System.out.println("Winner!");
		in.close();
	}
}
