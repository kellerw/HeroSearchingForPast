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
	public Action onWalkUp(Interactor i)
	{
		return new Action(o->{i.tryMoveUp(new Action()); o.start();});
	}
	public Action onWalkDown(Interactor i)
	{
		return new Action(o->{i.tryMoveDown(new Action()); o.start();});
	}
	public Action onWalkLeft(Interactor i)
	{
		return new Action(o->{i.tryMoveLeft(new Action()); o.start();});
	}
	public Action onWalkRight(Interactor i)
	{
		return new Action(o->{i.tryMoveRight(new Action()); o.start();});
	}
}
