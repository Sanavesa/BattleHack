package game;

import java.math.BigInteger;

public abstract class AbstractRobot
{
	public AbstractRobot(Game game)
	{
		this.game = game;
	}
	
	public abstract Command playTurn(Game game, Unit unit);
	
	public void log(String message)
	{
		System.out.println("[BATTLE HACK]: " + message);
	}
	
	public int[][] getVisibleRobotMap(Unit asker)
	{
		int mapSize = game.getMapSize();
		
		int[][] result = new int[mapSize][mapSize];
		for(int r = 0; r < mapSize; r++)
		{
			for(int c = 0; c < mapSize; c++)
			{
				result[r][c] = -1;
			}
		}
		
		int row = asker.getRow(asker);
		int col = asker.getCol(asker);
		
		for(int dr = -2; dr <= 2; dr++)
		{
			for(int dc = -2; dc <= 2; dc++)
			{
				if(dr == 0 && dc == 0)
					continue;
				
				int distance = Game.manhattanDistanceSqrd(0, 0, dr, dc);
				
				if(distance > 4)
					continue;
				
				result[row][col] = 0;
			}
		}
		
		for(Unit unit : game.getUnits())
		{
			if(unit.isVisible(asker))
			{
				
			}
		}
		
		return null;
	}
	
	public Unit getRobot(BigInteger id)
	{
		for(Unit unit : game.getUnits())
		{
			if(unit.getId().equals(id))
				return unit;
		}
		
		return null;
	}
	
	public boolean isVisible(Unit asker, BigInteger id)
	{
		Unit other = getRobot(id);
		
		if(other == null)
			return false;
		
		if(other.equals(asker))
			return true;
		
		return asker.equals(other);
	}
	
	private final Game game;
}