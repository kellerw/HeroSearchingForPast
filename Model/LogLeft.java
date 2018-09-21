public class LogLeft extends Log
{
	public LogLeft()
	{
		setSprite("logleft.png");
	}
	public LogLeft makeCopy()
	{
		return new LogLeft();
	}
	public String getClassName()
	{
		return "Log-Left";
	}
	public boolean canPushUpL(Interactor i)
	{
		return check(0, -1);
	}
	public boolean isWalkableUpL(Interactor i)
	{
		tryMoveUp(new Action());
		return false;
	}
	public boolean canPushDownL(Interactor i)
	{
		return check(0, 1);
	}
	public boolean isWalkableDownL(Interactor i)
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
		if(canPushUpR(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableUpR(i);
			tryMoveUp(new Action());
		}
		return false;
	}
	public boolean isWalkableDown(Interactor i)
	{
		if(canPushDownR(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableDownR(i);
			tryMoveDown(new Action());
		}
		return false;
	}
}
