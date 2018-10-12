import java.util.ArrayList;

public class Hand {
	
	private ArrayList<Card> hand = null;

	public Hand(Card[] cards) {
		this.hand = new ArrayList<Card>();
		
		for(int i = 0; i < cards.length; i++) {
			this.hand.add(cards[i]);
		}
	}
	
	public void addCard(Card card) {
		this.hand.add(card);
	}
	
	public void removeCard(Card card) {
		this.hand.remove(card);
	}
	
	public Card[] getHand() {
		return (Card[]) this.hand.toArray(new Card[this.hand.size()]);
	}
	
	public String toString() {
		String handString = "";
		for(int i = 0; i < this.hand.size(); i++) {
			if(i == 0) {
				handString += this.hand.get(i).toString();
			} else {
				handString += ", " + this.hand.get(i).toString();
			}
		}
		return handString;
	}
}
