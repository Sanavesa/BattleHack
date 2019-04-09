package testing;

import game.AbstractRobot;
import game.BuildCommand;
import game.BuildDirection;
import game.Command;
import game.Game;
import game.MoveCommand;
import game.MoveDirection;
import game.Unit;

public class TemplateRobot extends AbstractRobot
{

	@Override
	public Command playTurn(Game game, Unit unit)
	{
		// Everything you need is provided by game and unit.
		// You can try the autocomplete helper by pressing unit. then CTRL+Space to
		// see what functionality you have available.
		
		// Also, check out the other two robots.
		// The convolving isnt done, but its not bad
		// It needs a way of communicating to other units because they all
		// go to the same 'hot' spot.
		
		// Possible commands:
//		return new MoveCommand((Voyager)unit, MoveDirection.North);
//		return new BuildCommand((Planet)unit, BuildDirection.East);
		
		return null; // Means no command
	}

}
