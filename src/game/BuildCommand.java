package game;

public class BuildCommand implements Command
{
	public BuildCommand(Planet planet, BuildDirection buildDirection)
	{
		this.planet = planet;
		this.buildDirection = buildDirection;
	}
	
	@Override
	public void execute()
	{
		planet.buildVoyager(buildDirection);
	}

	public Planet getPlanet()
	{
		return planet;
	}
	
	public BuildDirection getBuildDirection()
	{
		return buildDirection;
	}
	
	private final Planet planet;
	private final BuildDirection buildDirection;
}