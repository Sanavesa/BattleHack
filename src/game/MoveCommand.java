package game;

public class MoveCommand implements Command
{
	public MoveCommand(Voyager voyager, MoveDirection moveDirection)
	{
		this.voyager = voyager;
		this.moveDirection = moveDirection;
	}
	
	@Override
	public void execute()
	{
		voyager.move(moveDirection);
	}
	
	public Voyager getVoyager()
	{
		return voyager;
	}
	
	public MoveDirection getMoveDirection()
	{
		return moveDirection;
	}
	
	private final Voyager voyager;
	private final MoveDirection moveDirection;
}