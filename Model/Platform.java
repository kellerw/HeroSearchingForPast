public class Platform extends Tile
{
	public Platform makeCopy()
	{
		return new Platform();
	}
	public String getClassName()
	{
		return "Platform";
	}
	public boolean isWalkable(Interactor i)
	{
		return true;
	}
}
