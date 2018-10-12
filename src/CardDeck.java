import java.util.concurrent.ThreadLocalRandom;

public class CardDeck {
	
	private int nextIndex = 0;
	private Card[] deck = new Card[52];
	
	public CardDeck() {
		int index = 0;
		
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				Card temp;
				try {
					temp = new Card(i, j);
					deck[index] = temp;
					index++;
				} catch (Exception e) {
					System.out.println("Error: That shouldn't have happened");
					e.printStackTrace();
				}
			}
		}
	}
	
	public void Shuffle() {
		int prevNum = ThreadLocalRandom.current().nextInt(0, 52);
		int randNum = ThreadLocalRandom.current().nextInt(0, 52);
		
		for(int i = 0; i < 400; i++) {
			Card temp = this.deck[prevNum];
			
			this.deck[prevNum] = this.deck[randNum];
			this.deck[randNum] = temp;
			
			prevNum = ThreadLocalRandom.current().nextInt(0, 52);
			randNum = ThreadLocalRandom.current().nextInt(0, 52);
		}
		this.nextIndex = 0;
	}
	
	public Card nextCard() throws NullPointerException {
		if(nextIndex > 51) {
			throw new NullPointerException("No cards left");
		}
		Card card = deck[this.nextIndex];
		this.nextIndex++;
		return card;
	}
	
	public int cardsLeft() {
		return 51 - this.nextIndex + 1;
	}
	
	public int cardsDrawn() {
		return this.nextIndex;
	}
}
