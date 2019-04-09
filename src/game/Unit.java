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
			if(unit.equals(this))
				continue;
			
			int r1 = getRow(this);
			int c1 = getCol(this);
			int r2 = unit.getRow(this);
			int c2 = unit.getCol(this);
			if(r1 == -1 || c1 == -1 || r2 == -1 || c2 == -1)
				continue;
			
			if(Game.manhattanDistanceSqrd(r1, c1, r2, c2) <= 4)
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
	
	public int getRow(Unit asker)
	{
		if(isVisible(asker))
			return row;
		else
			return -1;
	}

	protected void setRow(int row)
	{
		this.row = row;
	}

	public int getCol(Unit asker)
	{
		if(isVisible(asker))
			return col;
		else
			return -1;
	}

	protected void setCol(int col)
	{
		this.col = col;
	}

	public UnitTeam getUnitTeam(Unit asker)
	{
		if(isVisible(asker))
			return unitTeam;
		else
			return UnitTeam.None;
	}

	public void setSignal(int signal)
	{
		if(signal >= 0 && signal <= 65535)
		{
			this.signal = signal;
			signalChanged = true;
		}
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

	boolean signalChanged = false;
	private int signal;
	private int row;
	private int col;
	private final Game game;
	private final BigInteger id;
	private final UnitType unitType;
	private final UnitTeam unitTeam;
	private static final SecureRandom random = new SecureRandom();
}
