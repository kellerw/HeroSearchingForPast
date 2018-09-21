public class LogRight extends Log
{
	public LogRight()
	{
		setSprite("logright.png");
	}
	public LogRight makeCopy()
	{
		return new LogRight();
	}
	public String getClassName()
	{
		return "Log-Right";
	}
	public boolean canPushUpR(Interactor i)
	{
		return check(0, -1);
	}
	public boolean isWalkableUpR(Interactor i)
	{
		tryMoveUp(new Action());
		return false;
	}
	public boolean canPushDownR(Interactor i)
	{
		return check(0, 1);
	}
	public boolean isWalkableDownR(Interactor i)
	{
		tryMoveDown(new Action());
		return false;
	}
	public boolean isWalkableLeft(Interactor i)
	{
		return false;
	}
	public boolean isWalkableRight(Interactor i)
	{
		return false;
	}
	public boolean isWalkableUp(Interactor i)
	{
		if(canPushUpL(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableUpL(i);
			tryMoveUp(new Action());
		}
		return false;
	}
	public boolean isWalkableDown(Interactor i)
	{
		if(canPushDownL(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableDownL(i);
			tryMoveDown(new Action());
		}
		return false;
	}
}
