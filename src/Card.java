
public class Card implements Comparable<Card> {
	
		private int rank = -1;
		private int suit = -1;
		public static String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};
		public static String[] ranks = {"3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2"};

		public Card(int rank, int suit) throws IllegalArgumentException {
			if(rank > 12 || suit > 3) {
				throw new IllegalArgumentException("Incorrect params");
			}
			this.rank = rank;
			this.suit = suit;
		}
		
		public int[] card() {
			int[] card = {this.rank, this.suit};
			return card;
		}
		
		public String toString() {
			return Card.ranks[this.rank] + " Of " + Card.suits[this.suit];
		}
		
		public int getRank() {
			return this.rank;
		}
		
		public int getSuit() {
			return this.suit;
		}
		
		public int compareTo(Card card) {
			if(this.rank == card.card()[0]) {
				if(this.suit < card.card()[1]) {
					return -1;
				} else if(this.suit > card.card()[1]) {
					return 1;
				} else {
					return 0;
				}
			} else if(this.rank > card.card()[0]) {
				return 1;
			} else {
				return -1;
			}
		}
		
		public int compare(Card cardOne, Card cardTwo) {
			return cardOne.compareTo(cardTwo);
		}
		
		public boolean sameRank(Card card) {
			return this.rank == card.card()[0];
		}
		
		public boolean sameSuit(Card card) {
			return this.suit == card.card()[1];
		}
}
