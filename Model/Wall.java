public class Wall extends Base
{
	public Wall()
	{
		setSprite("brickwall.png");
	}
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
