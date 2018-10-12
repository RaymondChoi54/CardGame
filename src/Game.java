
public abstract class Game {
	
	abstract public Object possibleMoves();
	
	abstract public Boolean applyMove(int move);
	
	abstract public int currentTurn();
	
	abstract public String gameView();
}
