import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
//		Card card = new Card(1, 1);
//		Card card2 = new Card(2, 2);
//		
//		
//		System.out.println(card);
//		
//		System.out.println(card.compareTo(card2));
//		
//		CardDeck deck = new CardDeck();
//		
//		for(int i = 0; i < 52; i++) {
//			System.out.println(deck.nextCard());
//		}
//		
//		deck.Shuffle();
//		
//		for(int i = 0; i < 52; i++) {
//			try {
//				System.out.println(deck.nextCard());
//			} catch (Exception e) {
//				System.out.println("No cards left");
//			}
//		}
		Scanner in = new Scanner(System.in);
		
		int playerNum = 4;
		
		BigTwo game = new BigTwo(playerNum);
		
//		for(int i = 0; i < playerNum; i++) {
//			System.out.println("Player " + (i + 1) +":");
//			System.out.println(Arrays.toString(game.hand(i)));
//		}
////		
//		ArrayList<Card[]> moves = game.possibleMoves();
//		System.out.println(moves.size());
//		
//		for(int i = 0; i < moves.size(); i++) {
//			System.out.println(Arrays.toString(moves.get(i)));
//		}
//
//		game.applyMove(0);
//		
//		moves = game.possibleMoves();
//		System.out.println(moves.size());
//		
//		for(int i = 0; i < moves.size(); i++) {
//			System.out.println(Arrays.toString(moves.get(i)));
//		}
		
		while(game.currentTurn() != -1) {
			System.out.println("It is player " + game.currentTurn() + "'s turn");
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
