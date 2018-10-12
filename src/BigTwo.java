import java.util.ArrayList;
import java.util.Arrays;

public class BigTwo extends Game {
	
	private int numPlayers;
	private CardDeck deck;
	private Hand[] hands;
	private Card[] lastCards;
	private int currentTurn;
	private ArrayList<Card[]> possibleMoves;
	private boolean[] pass;
	
	public BigTwo(int numPlayers) {
		this.numPlayers = numPlayers;
		this.deck = new CardDeck();
		this.deck.Shuffle();
		this.hands = new Hand[numPlayers];
		for(int i = 0; i < numPlayers; i++) {
			this.hands[i] = new Hand(new Card[0]);
		}
		int currentPlayer = 0;
		while(this.deck.cardsLeft() > 0) {
			this.hands[currentPlayer].addCard(this.deck.nextCard());
			currentPlayer++;
			if(currentPlayer == numPlayers) {
				currentPlayer = 0;
			}
		}
		this.lastCards = null;
		this.currentTurn = 0;
		this.setPossibleMoves();
		this.pass = new boolean[numPlayers];
		for(int i = 0; i < numPlayers; i++) {
			pass[i] = false;
		}
	}
	
	public Card[] hand(int player) {
		return this.hands[player].getHand();
	}
	
	private boolean moveEqual(Card[] moveOne, Card[] moveTwo) {
		if(moveOne.length != moveTwo.length) {
			return false;
		}
		int numEqual = 0;
		for(int i = 0; i < moveOne.length; i++) {
			for(int j = 0; j < moveTwo.length; j++) {
				Card cardOne = moveOne[i];
				Card cardTwo = moveTwo[j];
				if(cardOne.compareTo(cardTwo) == 0) {
					numEqual++;
					break;
				}
			}
		}
		if(numEqual == moveOne.length) {
			return true;
		}
		return false;
		
	}
	
	private boolean inMoves(Card[] moveOne, ArrayList<Card[]> hand) {
		for(int i = 0; i < hand.size(); i++) {
			if(moveEqual(moveOne, hand.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	private Card[] mergeCards(Card card, Card[] cards) {
		Card[] temp = new Card[cards.length + 1];
		temp[0] = card;
		for(int i = 0; i < cards.length; i++) {
			temp[i + 1] = cards[i];
		}
		
		return temp;
	}
	
	private ArrayList<Card[]> allCombos(ArrayList<Card> cards, int length) {
		ArrayList<Card[]> result = new ArrayList<Card[]>();
		if(length == 1) {
			for(int i = 0; i < cards.size(); i++) {
				Card[] temp = {cards.get(i)};
				result.add(temp);
			}
		} else if(cards.size() == length) {
			result.add(cards.toArray(new Card[cards.size()]));
		} else if(cards.size() > length) {
			Card with = cards.get(0);
			cards.remove(0);
			ArrayList<Card[]> lengthMinus = allCombos(cards, length - 1);
			ArrayList<Card[]> lengthPlus = allCombos(cards, length);
			for(int i = 0; i < lengthMinus.size(); i++) {
				Card[] temp = mergeCards(with, lengthMinus.get(i));
				result.add(temp);
			}
			result.addAll(lengthPlus);
		} else {
			System.out.println("Error");
		}
		return result;
	}
	
	private boolean checkLevel(Card[] move) {
		if(this.lastCards == null) {
			return true;
		} else if(this.lastCards.length != move.length) {
			return false;
		} else {
			Card largest = this.lastCards[0];
			for(int i = 1; i < this.lastCards.length; i++) {
				if(largest.compareTo(this.lastCards[i]) == -1) {
					largest = this.lastCards[i];
				}
			}
			for(int i = 0; i < move.length; i++) {
				if(move[i].compareTo(largest) == 1) {
					return true;
				}
			}
			return false;
		}
	}
	
	// Nothing 0, Straight: 1, Flush: 2, Full House: 3, Four of a Kind: 4, Straight Flush: 5
	private int identifyPokerMoveRank(Card[] move) {
		if(move == null || move.length != 5) {
			return 0;
		}
		boolean inOrder = true;
		boolean sameSuit = true;
		boolean two = false;
		int sameRank = 0;
		
		Arrays.sort(move);
		
		Card firstCard = move[0];
		for(int i = 1; i < move.length; i++) {
			if(firstCard.getRank() != move[i].getRank() + i) {
				inOrder = false;
			}
			if(firstCard.getSuit() != move[i].getSuit()) {
				sameSuit = false;
			}
		}
		
		int[] ranks = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		for(int i = 0; i < move.length; i++) {
			ranks[move[i].getRank()]++;
		}
		for(int i = 0; i < ranks.length; i++) {
			if(ranks[i] > sameRank) {
				sameRank = ranks[i];
			}
			if(ranks[i] == 2) {
				two = true;
			}
		}
		
		if(inOrder && sameSuit) {
			return 5;
		} else if(sameRank == 4) {
			return 4;
		} else if(sameRank == 3 && two) {
			return 3;
		} else if(sameSuit) {
			return 2;
		} else if(inOrder) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private ArrayList<Card[]> pokerStraight(Card[] hand) {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		
		return moves;
	}
	
	private ArrayList<Card[]> pokerFlush(Card[] hand) {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		
		int lastRank = this.identifyPokerMoveRank(this.lastCards);
		Card toBeat = null;
		// Get the rank to beat if previous move is a Flush
		if(lastRank == 2) {
			for(int i = 0; i < this.lastCards.length; i++) {
				if(toBeat == null || this.lastCards[i].compareTo(toBeat) == 1) {
					toBeat = this.lastCards[i];
				}
			}
		}
		
		if(lastRank <= 2) {
			int[] suitCount = {0, 0, 0, 0};
			for(int i = 0; i < hand.length; i++) {
				suitCount[hand[i].getSuit()]++;
			}
			for(int i = 0; i < suitCount.length; i++) {
				if(suitCount[i] >= 5) {
					ArrayList<Card> allOfSuit = new ArrayList<Card>();
					for(int j = 0; j < hand.length; j++) {
						if(hand[j].getSuit() == i) {
							allOfSuit.add(hand[j]);
						}
					}
					
					ArrayList<Card[]> combos = allCombos(allOfSuit, 5);
					for(int j = 0; j < combos.size(); j++) {
						Card[] move = combos.get(j);
						if(lastRank == 2) {
							for(int x = 0; x < move.length; x++) {
								Card card = move[x];
								if(toBeat.compareTo(card) == -1) {
									moves.add(move);
									break;
								}
							}
						} else {
							moves.add(move);
						}
					}
					//moves.addAll(allCombos(allOfSuit, 5));
				}
			}
		}
		
		return moves;
	}
	
	private ArrayList<Card[]> pokerFullHouse(Card[] hand) {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		
		int lastRank = this.identifyPokerMoveRank(this.lastCards);
		int toBeat = -1;
		// Get the rank to beat if previous move is a Full House
		if(lastRank == 3) {
			int[] ranks = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for(int i = 0; i < this.lastCards.length; i++) {
				ranks[this.lastCards[i].getRank()]++;
			}
			for(int i = 0; i < ranks.length; i++) {
				if(ranks[i] == 3) {
					toBeat = i;
				}
			}
		}
		
		if(this.lastCards == null || lastRank <= 3) {
			ArrayList<Card[]> doubles = this.matchRank(2, true);
			ArrayList<Card[]> triples = this.matchRank(3, true);
			
			if(triples.size() != 0 && doubles.size() !=0) {
				for(int i = 0; i < triples.size(); i++) {
					Card[] tripleCards = triples.get(i);
					for(int j = 0; j < doubles.size(); j++) {
						Card[] doubleCards = doubles.get(j);
						if(!tripleCards[0].sameRank(doubleCards[0]) && (lastRank < 3 || tripleCards[0].getRank() > toBeat)) {
							Card[] temp = {tripleCards[0], tripleCards[1], tripleCards[2], doubleCards[0], doubleCards[1]};
							moves.add(temp);
						}
					}
				}
			}
		}
		
		return moves;
	}
	
	private ArrayList<Card[]> pokerFourKind(Card[] hand) {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		
		int lastRank = this.identifyPokerMoveRank(this.lastCards);
		int toBeat = -1;
		// Get the rank to beat if previous move is a Four of a Kind
		if(lastRank == 4) {
			int[] ranks = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for(int i = 0; i < this.lastCards.length; i++) {
				ranks[this.lastCards[i].getRank()]++;
			}
			for(int i = 0; i < ranks.length; i++) {
				if(ranks[i] == 4) {
					toBeat = i;
				}
			}
		}
		
		if(this.lastCards == null || lastRank <= 4) {
			ArrayList<Card[]> quads = this.matchRank(4, true);
			for(int i = 0; i < quads.size(); i++) {
				Card[] quad = quads.get(i);
				for(int j = 0; j < hand.length; j++) {
					Card single = hand[j];
					if(quad[0].getRank() != single.getRank() && (lastRank < 4 || quad[0].getRank() > toBeat)) {
						Card[] temp = {quad[0], quad[1], quad[2], quad[3], single};
						moves.add(temp);
					}
				}
			}
		}
		
		return moves;
	}
	private ArrayList<Card[]> pokerSFlush(Card[] hand) {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		
		return moves;
	}
	
	private ArrayList<Card[]> pokerMove() {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		Card[] current = this.hands[this.currentTurn].getHand();

		moves.addAll(pokerStraight(current));
		moves.addAll(pokerFlush(current));
		moves.addAll(pokerFullHouse(current));
		moves.addAll(pokerFourKind(current));
		moves.addAll(pokerSFlush(current));
		
		return moves;
	}
	
	private ArrayList<Card[]> matchRank(int numMatch, boolean any) {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		Card[] current = this.hands[this.currentTurn].getHand();
		for(int i = 0; i < current.length; i++) {
			ArrayList<Card> sameRank = new ArrayList<Card>();
			Card cardOne = current[i];
			sameRank.add(cardOne);
			// Find cards of all the same rank
			for(int j = 0; j < current.length; j++) {
				Card cardTwo = current[j];
				if(cardOne.sameRank(cardTwo) && !cardOne.sameSuit(cardTwo)) {
					sameRank.add(cardTwo);
				}
			}
			// Add all combinations to moves if not already in moves
			if(sameRank.size() >= numMatch) {
				ArrayList<Card[]> possibleMoves = allCombos(sameRank, numMatch);
				for(int j = 0; j < possibleMoves.size(); j++) {
					if(!inMoves(possibleMoves.get(j), moves) && (this.checkLevel(possibleMoves.get(j)) || any)) {
						moves.add(possibleMoves.get(j));
					}
				}
			}
		}
		return moves;

	}
	
	private ArrayList<Card[]> matchRank(int numMatch) {
		return matchRank(numMatch, false);
	}
	
	private ArrayList<Card[]> possibleSingles() {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		Card[] current = this.hands[this.currentTurn].getHand();
		if(this.lastCards == null) {
			for(int i = 0; i < current.length; i++) {
				Card[] move = {current[i]};
				moves.add(move);
			}
		} else if(this.lastCards.length == 1) {
			for(int i = 0; i < current.length; i++) {
				if(current[i].compareTo(lastCards[0]) == 1) {
					Card[] move = {current[i]};
					moves.add(move);
				}
			}
		}
		return moves;
	}
	
	private void setPossibleMoves() {
		ArrayList<Card[]> moves = new ArrayList<Card[]>();
		if(this.lastCards == null) {
			moves.addAll(this.possibleSingles());
			moves.addAll(this.matchRank(2));
			moves.addAll(this.matchRank(3));
			moves.addAll(this.matchRank(4));
			moves.addAll(this.pokerMove());
		} else if(this.lastCards.length == 1) {
			moves.addAll(this.possibleSingles());
		} else if(this.lastCards.length == 2) {
			moves.addAll(this.matchRank(2));
		} else if(this.lastCards.length == 3) {
			moves.addAll(this.matchRank(3));
		} else if(this.lastCards.length == 4) {
			moves.addAll(this.matchRank(4));
		} else if(this.lastCards.length == 5) {
			moves.addAll(this.pokerMove());
		}
		this.possibleMoves = moves;
	}
	
	private void removeFromHand(Card[] cards) {
		for(int i = 0; i < cards.length; i++) {
			this.hands[this.currentTurn].removeCard(cards[i]);
		}
	}
	
	private void setTurn(int move) {
		if(move == -1) {
			this.pass[this.currentTurn] = true;
		}
		int noPass = 0;
		int nextTurn = 0;
		for(int i = 0; i < this.pass.length; i++) {
			if(!this.pass[i]) {
				noPass++;
				nextTurn = i;
			}
		}
		
		if(noPass == 1) {
			this.currentTurn = nextTurn;
			this.lastCards = null;
			for(int i = 0; i < numPlayers; i++) {
				pass[i] = false;
			}
		} else {
			this.currentTurn++;
			if(this.currentTurn >= this.numPlayers) {
				this.currentTurn = 0;
			}
			while(this.pass[this.currentTurn]) {
				this.currentTurn++;
				if(this.currentTurn >= this.numPlayers) {
					this.currentTurn = 0;
				}
			}
		}
		this.setPossibleMoves();
	}

	@Override
	public ArrayList<Card[]> possibleMoves() {
		return this.possibleMoves;
	}

	@Override
	public Boolean applyMove(int move) {
		if(move >= -1 && move < this.possibleMoves.size()) {
			if(move != -1) {
				this.lastCards = this.possibleMoves.get(move);
				this.removeFromHand(this.lastCards);
				if(this.hands[this.currentTurn].getHand().length == 0) {
					this.currentTurn = -1;
					return true;
				}
			}
			this.setTurn(move);
			return true;
		}
		return false;
	}

	@Override
	public int currentTurn() {
		return this.currentTurn;
	}
	
	public Card[] currentHand() {
		return this.hands[this.currentTurn].getHand();
	}

	@Override
	public String gameView() {
		// TODO Auto-generated method stub
		return null;
	}

}
