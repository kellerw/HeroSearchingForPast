public class LogTop extends Log
{
	public LogTop()
	{
		setSprite("logtop.png");
	}
	public LogTop makeCopy()
	{
		return new LogTop();
	}
	public String getClassName()
	{
		return "Log-Top";
	}
	public boolean canPushLeftU(Interactor i)
	{
		return check(-1, 0);
	}
	public boolean isWalkableLeftU(Interactor i)
	{
		tryMoveLeft(new Action());
		return false;
	}
	public boolean canPushRightU(Interactor i)
	{
		return check(1, 0);
	}
	public boolean isWalkableRightU(Interactor i)
	{
		tryMoveRight(new Action());
		return false;
	}
	public boolean isWalkableUp(Interactor i)
	{
		return false;
	}
	public boolean isWalkableDown(Interactor i)
	{
		return false;
	}
	public boolean isWalkableLeft(Interactor i)
	{
		if(canPushLeftD(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableLeftD(i);
			tryMoveLeft(new Action());
		}
		return false;
	}
	public boolean isWalkableRight(Interactor i)
	{
		if(canPushRightD(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableRightD(i);
			tryMoveRight(new Action());
		}
		return false;
	}
}
