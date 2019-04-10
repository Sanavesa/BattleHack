package game;

public class Voyager extends Unit
{
	public Voyager(Game game, UnitTeam unitTeam, int row, int col)
	{
		super(game, UnitType.Voyager, unitTeam, row, col);
	}

	public boolean canMove(MoveDirection direction)
	{
		int moveRow = getRow() + direction.getDeltaRow();
		int moveCol = getCol() + direction.getDeltaCol();
		
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
			
			// I dont care about enemy team
			if(unit.getUnitTeam() != getUnitTeam())
				continue;
			
//			if(Game.manhattanDistanceSqrd(moveRow, moveCol, unit.getRow(), unit.getCol()) <= 4)
			if(Math.abs(moveRow - unit.getRow()) <= 1 && Math.abs(moveCol - unit.getCol()) <= 1)
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
			int moveRow = getRow() + direction.getDeltaRow();
			int moveCol = getCol() + direction.getDeltaCol();
			setRow(moveRow);
			setCol(moveCol);
		}
	}
}