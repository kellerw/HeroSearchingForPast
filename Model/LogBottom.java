public class LogBottom extends Log
{
	public LogBottom()
	{
		setSprite("logbottom.png");
	}
	public LogBottom makeCopy()
	{
		return new LogBottom();
	}
	public String getClassName()
	{
		return "Log-Bottom";
	}
	public boolean canPushLeftD(Interactor i)
	{
		return check(-1, 0);
	}
	public boolean isWalkableLeftD(Interactor i)
	{
		tryMoveLeft(new Action());
		return false;
	}
	public boolean canPushRightD(Interactor i)
	{
		return check(1, 0);
	}
	public boolean isWalkableRightD(Interactor i)
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
		if(canPushLeftU(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableLeftU(i);
			tryMoveLeft(new Action());
		}
		return false;
	}
	public boolean isWalkableRight(Interactor i)
	{
		if(canPushRightU(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableRightU(i);
			tryMoveRight(new Action());
		}
		return false;
	}
}
