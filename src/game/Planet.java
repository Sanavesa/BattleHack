package game;

public class Planet extends Unit
{
	public Planet(Game game, UnitTeam unitTeam, int row, int col)
	{
		super(game, UnitType.Planet, unitTeam, row, col);
	}

	public boolean canBuildVoyager(BuildDirection direction)
	{
		if(orbCount < Planet.BUILD_COST)
			return false;
		
		// Can only build in adjacent, empty, and passable tiles
		int buildRow = getRow() + direction.getDeltaRow();
		int buildCol = getCol() + direction.getDeltaCol();
		
		if(buildRow < 0 || buildCol < 0 || buildRow >= getGame().getMapSize() || buildCol >= getGame().getMapSize())
			return false;
		
		if(getGame().isEmpty(buildRow, buildCol) && getGame().isPassable(buildRow, buildCol))
			return true;
		else
			return false;
	}
	
	void buildVoyager(BuildDirection direction)
	{
		if(canBuildVoyager(direction))
		{
			int buildRow = getRow() + direction.getDeltaRow();
			int buildCol = getCol() + direction.getDeltaCol();
			
			Voyager voyager = new Voyager(getGame(), getUnitTeam(), buildRow, buildCol);
			getGame().addUnit(voyager);
			
			orbCount -= Planet.BUILD_COST;
		}
	}
	
	public int getOrbCount()
	{
		return orbCount;
	}
	
	public int getTotalOrbsCollected()
	{
		return totalOrbsCollected;
	}

	void awardOrbs(int count)
	{
		orbCount += count;
		totalOrbsCollected += count;
	}
	
	private int orbCount = BUILD_COST;
	private int totalOrbsCollected = 0;
	public static final int BUILD_COST = 65536;
}