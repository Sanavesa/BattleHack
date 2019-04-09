package game;

public enum BuildDirection
{
	North(-1, 0),
	South(1, 0),
	East(0, 1),
	West(0, -1),
	NorthWest(-1, -1),
	NorthEast(-1, 1),
	SouthWest(1, -1),
	SouthEast(1, 1);
	
	private BuildDirection(int deltaRow, int deltaCol)
	{
		this.deltaRow = deltaRow;
		this.deltaCol = deltaCol;
	}
	
	public int getDeltaRow()
	{
		return deltaRow;
	}
	
	public int getDeltaCol()
	{
		return deltaCol;
	}

	private final int deltaRow;
	private final int deltaCol;
}