package game;

import testing.RandomRobot;

public class ConsoleProgram
{
	public static void main(String[] args)
	{
		System.out.println("BattleHack");
		Game game = new Game(new RandomRobot(), new RandomRobot(), 10, Orientation.Horizontal);
		game.displayOrbs();
		game.displayUnits();
		game.playGame();
	}
}
