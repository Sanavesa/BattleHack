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

public class DannysTestRob extends AbstractRobot
{
	@Override
	public Command playTurn(Game game, Unit unit)
	{
		if(unit.getUnitType() == UnitType.Voyager)
		{
			Voyager voyager = (Voyager) unit;
			MoveDirection randomDirection = getRandomMoveDirection();
			
			// Try random direction, but if it fails (blocked, impassable etc) we try any other action that will succeed
			if(voyager.canMove(randomDirection))
			{
				return new MoveCommand(voyager, randomDirection);
			}
			else
			{
				for(MoveDirection direction : moveDirections)
				{
					if(voyager.canMove(direction))
					{
						return new MoveCommand(voyager, direction);
					}
				}
			}
		}
		else
		{
			Planet planet = (Planet) unit;
			BuildDirection dir = getRandomBuildDirection();
			
			// Try random direction, but if it fails (blocked, impassable etc) we try any other action that will succeed
			if(planet.canBuildVoyager(dir))
			{
				return new BuildCommand(planet, dir);
			}
			else
			{
				for(BuildDirection direction : buildDirections)
				{
					if(planet.canBuildVoyager(direction))
					{
						return new BuildCommand(planet, direction);
					}
				}
			}
		}
		
		// Do nothing (no actions) for this specific unit
		return null;
	}
	
	private MoveDirection getRandomMoveDirection()
	{
		int i = (int) (Math.random() * moveDirections.length);
		return moveDirections[i];
	}
	
	private BuildDirection getRandomBuildDirection()
	{
		int i = (int) (Math.random() * buildDirections.length);
		return buildDirections[i];
	}
	
	private static final MoveDirection[] moveDirections = MoveDirection.values();
	private static final BuildDirection[] buildDirections = BuildDirection.values();
}