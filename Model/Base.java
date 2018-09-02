public class Base extends Walkable
{
	public Base()
	{
		setSprite("brick.png");
	}
	public Base makeCopy()
	{
		return new Base();
	}
	public String getClassName()
	{
		return "Base";
	}
	public boolean isBase()
	{
		return true;
	}
}
