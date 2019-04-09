package game;

public enum MoveDirection
{
	North(-1, 0),
	South(1, 0),
	East(0, 1),
	West(0, -1);
	
	private MoveDirection(int deltaRow, int deltaCol)
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