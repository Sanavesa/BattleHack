package game;

public class Voyager extends Unit
{
	public Voyager(Game game, UnitTeam unitTeam, int row, int col)
	{
		super(game, UnitType.Voyager, unitTeam, row, col);
	}

	public boolean canMove(MoveDirection direction)
	{
		int moveRow = getRow(this) + direction.getDeltaRow();
		int moveCol = getCol(this) + direction.getDeltaCol();
		
		if(moveRow < 0 || moveCol < 0 || moveRow >= getGame().getMapSize() || moveCol >= getGame().getMapSize())
			return false;
		
		boolean canMove = true;
		
		if(!getGame().isEmpty(moveRow, moveCol))
			return false;
		
		if(!getGame().isPassable(moveRow, moveCol))
			return false;
		
		for(Unit unit : getGame().getUnits())
		{
			if(unit.equals(this))
				continue;
			
			if(unit.getUnitType() == UnitType.Planet)
				continue;
			
			// I dont care about enemy team / invisible units
			UnitTeam otherTeam = unit.getUnitTeam(this);
			if(otherTeam != getUnitTeam(this) || otherTeam == UnitTeam.None)
				continue;
			
			if(Math.abs(moveRow - unit.getRow(this)) > 1 || Math.abs(moveCol - unit.getCol(this)) > 1)
			{
				canMove = false;
				break;
			}
		}
		
		return canMove;
	}
	
	void move(MoveDirection direction)
	{
		if(canMove(direction))
		{
			int moveRow = getRow(this) + direction.getDeltaRow();
			int moveCol = getCol(this) + direction.getDeltaCol();
			setRow(moveRow);
			setCol(moveCol);
		}
	}
}