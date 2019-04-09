package testing;

import game.AbstractRobot;
import game.BuildCommand;
import game.BuildDirection;
import game.Command;
import game.Game;
import game.MoveCommand;
import game.MoveDirection;
import game.Planet;
import game.Unit;
import game.UnitType;
import game.Voyager;

public class ConvolvingRobot extends AbstractRobot
{
	@Override
	public Command playTurn(Game game, Unit unit)
	{
		if(unit.getUnitType() == UnitType.Voyager)
		{
			// Finds hottest place (heatmap-speaking) and goes there
			Voyager voyager = (Voyager) unit;
			
			int[][] convolvedOrbs = convolve(game, 5);
			
			// Find maximum value, and go to it.
			int maxRow = -1;
			int maxCol = -1;
			int maxValue = Integer.MIN_VALUE;
			
			int mapSize = game.getMapSize();
			for(int r = 0; r < mapSize; r++)
			{
				for(int c = 0; c < mapSize; c++)
				{
					int value = convolvedOrbs[r][c];
					if(value > maxValue)
					{
						maxRow = r;
						maxCol = c;
						maxValue = value;
					}
					else if(value == maxValue) // if its the same hotness, choose closest to unit
					{
						int distance = Game.manhattanDistance(r, c, unit.getRow(unit), unit.getCol(unit));
						int otherDistance = Game.manhattanDistance(maxRow, maxCol, unit.getRow(unit), unit.getCol(unit));
						if(distance < otherDistance)
						{
							maxRow = r;
							maxCol = c;
							maxValue = value;
						}
					}
				}
			}
			
			// Go to maxRow, maxCol
			int deltaRow = maxRow - unit.getRow(unit);
			int deltaCol = maxCol - unit.getCol(unit);
			
			// Preferablly, use A* for pathfinding, but im gonna use Crap*
			// notice its not if-else below, its a bunch of ifs, incase one fails, others can still be examined
			
			if(deltaCol > 0) // move right
			{
				if(voyager.canMove(MoveDirection.East))
					return new MoveCommand(voyager, MoveDirection.East);
			}
			
			if(deltaCol < 0) // move left
			{
				if(voyager.canMove(MoveDirection.West))
					return new MoveCommand(voyager, MoveDirection.West);
			}
			
			if(deltaRow > 0) // move down
			{
				if(voyager.canMove(MoveDirection.South))
					return new MoveCommand(voyager, MoveDirection.South);
			}
			
			if(deltaRow < 0) // move up
			{
				if(voyager.canMove(MoveDirection.North))
					return new MoveCommand(voyager, MoveDirection.North);
			}
		}
		else
		{
			Planet planet = (Planet) unit;
			
			// Simply build whenever possible at any location
			for(BuildDirection direction : BuildDirection.values())
			{
				if(planet.canBuildVoyager(direction))
				{
					return new BuildCommand(planet, direction);
				}
			}
		}

		// Do nothing for this specific unit
		return null;
	}
	
	private int[][] convolve(Game game, int kernelSize)
	{
		// Will use a filter kernel size of 5x5 just cuz.
//		int kernelSize = 5;
		
		// when u apply a convolving filter, the grid size shrinks
		// If it was N x N grid before, it will become (N-k+1) x (N-k+1) where k = kernel size
		// To combat that, we can pad stuff with 0 so we can maintaing N x N grid
		
		// The code below will convolve but shrink, we dont want that
//		int newMapSize = game.getMapSize() - kernelSize + 1;
//		
//		int[][] convolvedOrbs = new int[newMapSize][newMapSize];
//		
//		for(int r = 0; r < newMapSize; r++)
//		{
//			for(int c = 0; c < newMapSize; c++)
//			{
//				int sum = 0;
//				
//				for(int rFilter = 0; rFilter < kernelSize; rFilter++)
//				{
//					for(int cFilter = 0; cFilter < kernelSize; cFilter++)
//					{
//						sum += game.getOrbValueAt(r + rFilter, c + cFilter);
//					}
//				}
//				
//				convolvedOrbs[r][c] = sum;
//			}
//		}
//		
//		return convolvedOrbs;
		
		// The code below will convolve but maintain the size of the grid
		int mapSize = game.getMapSize();
		int[][] convolvedOrbs = new int[mapSize][mapSize];
		
		for(int r = 0; r < mapSize; r++)
		{
			for(int c = 0; c < mapSize; c++)
			{
				int sum = 0;
				
				for(int rFilter = 0; rFilter < kernelSize; rFilter++)
				{
					for(int cFilter = 0; cFilter < kernelSize; cFilter++)
					{
						// As mentioned earlier, this is a cheap trick
						// to pad the convolving process with zeros
						// on the edge so that we can maintain the 
						// grid size
						if(!game.isOutOfBounds(r + rFilter, c + cFilter))
						{
							sum += game.getOrbValueAt(r + rFilter, c + cFilter);
						}
					}
				}
				
				convolvedOrbs[r][c] = sum;
			}
		}
		
		return convolvedOrbs;
	}
}