package game;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public abstract class Unit
{
	public Unit(Game game, UnitType unitType, UnitTeam unitTeam, int row, int col)
	{
		// Generate 4096 bits integer
		byte[] bytes = new byte[512];
		random.nextBytes(bytes);
		id = new BigInteger(bytes);
		
		this.game = game;
		this.unitType = unitType;
		this.unitTeam = unitTeam;
		this.row = row;
		this.col = col;
		signal = 0;
	}
	
	public Unit[] getNearbyUnits()
	{
		List<Unit> nearbyUnits = new ArrayList<>();
		
		for(Unit unit : game.getUnits())
		{
			if(Game.manhattanDistanceSqrd(getRow(), getCol(), unit.getRow(), unit.getCol()) <= 4)
			{
				nearbyUnits.add(unit);
			}
		}
		
		return nearbyUnits.toArray(new Unit[nearbyUnits.size()]);
	}
	
	public boolean isVisible(Unit other)
	{
		for(Unit unit : getNearbyUnits())
		{
			if(unit.equals(other))
				return true;
		}
		return false;
	}
	
	public int getRow()
	{
		return row;
	}

	protected void setRow(int row)
	{
		this.row = row;
	}

	public int getCol()
	{
		return col;
	}

	protected void setCol(int col)
	{
		this.col = col;
	}

	public UnitTeam getUnitTeam()
	{
		return unitTeam;
	}

	public void setSignal(int signal)
	{
		this.signal = signal;
	}
	
	public int getSignal()
	{
		return signal;
	}

	public BigInteger getId()
	{
		return id;
	}
	
	public UnitType getUnitType()
	{
		return unitType;
	}
	
	protected Game getGame()
	{
		return game;
	}

	private int signal;
	private int row;
	private int col;
	private final Game game;
	private final BigInteger id;
	private final UnitType unitType;
	private final UnitTeam unitTeam;
	private static final SecureRandom random = new SecureRandom();
}
