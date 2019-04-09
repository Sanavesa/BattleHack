package game;

public abstract class AbstractRobot
{
	public abstract Command playTurn(Game game, Unit unit);
}