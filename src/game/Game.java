package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Game
{
	public Game(AbstractRobot redRobot, AbstractRobot blueRobot, int mapSize, Orientation symmetryOrientation)
	{
		this.redRobot = redRobot;
		this.blueRobot = blueRobot;
		this.mapSize = mapSize;
		this.symmetryOrientation = symmetryOrientation;
		
		units = new LinkedList<>();
		temporaryAddedUnits = new LinkedList<>();
		roundCounter = 0;
		orbMap = new int[mapSize][mapSize];
		
		createOrbMap();
		createCivilizations();
	}
	
	void playGame()
	{
		for(int round = 0; round < Game.MAX_ROUNDS; round++)
		{
			playRound();
		}
	}
	
	void restart()
	{
		units.clear();
		temporaryAddedUnits.clear();
		roundCounter = 0;
		createOrbMap();
		createCivilizations();
	}
	
	public boolean[][] getPassableMap()
	{
		boolean[][] map = new boolean[mapSize][mapSize]; // all false
		
		for(int r = 0; r < mapSize; r++)
		{
			for(int c = 0; c < mapSize; c++)
			{
				if(orbMap[r][c] > 0) // passable
				{
					map[r][c] = true;
				}
			}
		}
		
		return map;
	}
	
	void playRound()
	{
		roundCounter++;
		
		// Reset signals
//		for(Unit unit : units)
//		{
//			unit.setSignal(0);
//		}
		
		// Play turn in order 
		for(Unit unit : units)
		{
			if(unit.getUnitTeam(unit) == UnitTeam.Red)
			{
				Command command = redRobot.playTurn(this, unit);
				if(command != null)
				{
					command.execute();
				}
			}
			else
			{
				Command command = blueRobot.playTurn(this, unit);
				if(command != null)
				{
					command.execute();
				}
			}
			
			if(!unit.signalChanged)
			{
				unit.setSignal(0);
				unit.signalChanged = false;
			}
		}
		
		// Process added units
		while(temporaryAddedUnits.size() > 0)
		{
			Queue<Unit> temp = new LinkedList<>();
			temp.addAll(temporaryAddedUnits);
			units.addAll(temporaryAddedUnits);
			temporaryAddedUnits.clear();
			
			for(Unit unit : temp)
			{
				if(unit.getUnitTeam(unit) == UnitTeam.Red)
				{
					redRobot.playTurn(this, unit);
				}
				else
				{
					blueRobot.playTurn(this, unit);
				}
				
				if(!unit.signalChanged)
				{
					unit.setSignal(0);
					unit.signalChanged = false;
				}
			}
		}
		
		// Pickup orbs
		List<Unit> closestUnits = new ArrayList<>();
		int closestDistance = Integer.MAX_VALUE;
		
		for(int r = 0; r < mapSize; r++)
		{
			for(int c = 0; c < mapSize; c++)
			{
				int orbs = orbMap[r][c];
				
				if(orbs == 0)
					continue;
				
				closestDistance = Integer.MAX_VALUE;
				closestUnits.clear();
				
				// Find closest distance unit
				for(Unit unit : units)
				{
					if(unit.getUnitType() == UnitType.Planet)
						continue;
					
					int distance = manhattanDistance(r, c, unit.getRow(unit), unit.getCol(unit));
					if(distance < closestDistance)
					{
						closestDistance = distance;
						closestUnits.clear();
						closestUnits.add(unit);
					}
					else if(distance == closestDistance)
					{
						closestUnits.add(unit);
					}
				}
				
				if(closestUnits.size() == 0)
					continue;
				
				UnitTeam closestTeam = closestUnits.get(0).getUnitTeam(closestUnits.get(0));
				boolean allSameTeam = true;
				for(int i = 1; i < closestUnits.size(); i++)
				{
					if(closestTeam != closestUnits.get(i).getUnitTeam(closestUnits.get(i)))
					{
						allSameTeam = false;
						break;
					}
				}
				
				if(allSameTeam)
				{
					if(closestTeam == UnitTeam.Red)
					{
						redPlanet.awardOrbs(orbs);
					}
					else
					{
						bluePlanet.awardOrbs(orbs);
					}
				}
			}
		}
	}
	
	public void setRedRobot(AbstractRobot x)
	{
		this.redRobot = x;
	}
	
	public void setBlueRobot(AbstractRobot x)
	{
		this.blueRobot = x;
	}
	
	public int getOrbValueAt(int row, int col)
	{
		return orbMap[row][col];
	}
	
	public boolean isOutOfBounds(int row, int col)
	{
		return (row < 0) || (col < 0) || (row >= mapSize) || (col >= mapSize);
	}
	
	public static int manhattanDistance(int r1, int c1, int r2, int c2)
	{
		return Math.abs(r1-r2) + Math.abs(c1-c2);
	}
	
	public static int manhattanDistanceSqrd(int r1, int c1, int r2, int c2)
	{
		int r = r1-r2;
		int c = c1-c2;
		return r*r + c*c;
	}
	
	public int[][] getOrbMap()
	{
		int[][] map = new int[mapSize][mapSize];
		
		for(int r = 0; r < mapSize; r++)
		{
			for(int c = 0; c < mapSize; c++)
			{
				map[r][c] = orbMap[r][c];
			}
		}
		
		return map;
	}
	
	public int getMapSize()
	{
		return mapSize;
	}

	public Unit[] getUnits()
	{
		return units.toArray(new Unit[units.size()]);
	}

	public boolean isPassable(int row, int col)
	{
		return orbMap[row][col] > 0;
	}
	
	public boolean isEmpty(int row, int col)
	{
		for(Unit unit : units)
		{
			if(unit.getRow(unit) == row && unit.getCol(unit) == col)
				return false;
		}
		
		return true;
	}
	
	Unit getUnitAt(int row, int col)
	{
		for(Unit unit : units)
		{
			if(unit.getRow(unit) == row && unit.getCol(unit) == col)
				return unit;
		}
		
		return null;
	}
	
	public Orientation getSymmetryOrientation()
	{
		return symmetryOrientation;
	}

	public static int getMaxRounds()
	{
		return MAX_ROUNDS;
	}

	public int getRoundCounter()
	{
		return roundCounter;
	}
	
	void addUnit(Unit unit)
	{
		temporaryAddedUnits.add(unit);
	}
	
	private void createOrbMap()
	{
		// Fill the entire map non-uniformally
		for(int r = 0; r < mapSize; r++)
		{
			for(int c = 0; c < mapSize; c++)
			{
				orbMap[r][c] = (int) (Math.random() * 8); // [0, 7] inclusive;
			}
		}
		
		// Based on symmetry orientation, copy the values (CHEESY BAD QUICK METHOD)
		if(symmetryOrientation == Orientation.Horizontal)
		{
			int centerColumn = mapSize / 2;
			for(int r = 0; r < mapSize; r++)
			{
				for(int c = centerColumn; c < mapSize; c++)
				{
					orbMap[r][c] = orbMap[r][mapSize - c - 1];
				}
			}
		}
		else
		{
			int centerRow = mapSize / 2;
			
			for(int r = centerRow; r < mapSize; r++)
			{
				for(int c = 0; c < mapSize; c++)
				{
					orbMap[r][c] = orbMap[mapSize - r - 1][c];
				}
			}
		}
	}
	
	Planet getRedPlanet()
	{
		return redPlanet;
	}

	Planet getBluePlanet()
	{
		return bluePlanet;
	}

	private void createCivilizations()
	{
		int redRow = -1;
		int redCol = -1;
		do
		{
			redRow = (int) (Math.random() * mapSize);
			redCol = (int) (Math.random() * mapSize);
		}
		while(!isEmpty(redRow, redCol));
		redPlanet = new Planet(this, UnitTeam.Red, redRow, redCol);
		units.add(redPlanet);
		
		int blueRow = redRow;
		int blueCol = redCol;
		if(symmetryOrientation == Orientation.Horizontal)
		{
			blueCol = mapSize - blueCol - 1;
		}
		else
		{
			blueRow = mapSize - blueRow - 1;
		}
		bluePlanet = new Planet(this, UnitTeam.Blue, blueRow, blueCol);
		units.add(bluePlanet);
	}
	
	private int roundCounter;
	private final int mapSize;
	private final Queue<Unit> units;
	private final Queue<Unit> temporaryAddedUnits;
	private final int[][] orbMap;
	private final Orientation symmetryOrientation;
	private Planet redPlanet;
	private Planet bluePlanet;
	private AbstractRobot redRobot;
	private AbstractRobot blueRobot;
	public static final int MAX_ROUNDS = 256;
}
