public class Ice extends Base
{
	public Ice()
	{
		setSprite("ice.png");
	}
	public Ice makeCopy()
	{
		return new Ice();
	}
	public String getClassName()
	{
		return "Ice";
	}
	public void onWalkUp(Interactor i)
	{
		i.tryMoveUp(new Action(null));
	}
	public void onWalkDown(Interactor i)
	{
		i.tryMoveDown(new Action(null));
	}
	public void onWalkLeft(Interactor i)
	{
		i.tryMoveLeft(new Action(null));
	}
	public void onWalkRight(Interactor i)
	{
		i.tryMoveRight(new Action(null));
	}
}
