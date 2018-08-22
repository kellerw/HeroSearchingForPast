public class Wall extends Base
{
	public Wall makeCopy()
	{
		return new Wall();
	}
	public String getClassName()
	{
		return "Wall";
	}
	public boolean isWalkable(Interactor i)
	{
		return false;
	}
}
