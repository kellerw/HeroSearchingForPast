public abstract class Log extends Interactor
{
	public boolean canPushUpL(Interactor i)
	{
		return check(0, -1) && ((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).canPushUpL(i);
	}
	public boolean canPushUpR(Interactor i)
	{
		return check(0, -1) && ((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).canPushUpR(i);
	}
	public boolean isWalkableUp(Interactor i)
	{
		if(canPushUpL(i) && canPushUpR(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableUpL(i);
			((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableUpR(i);
			tryMoveUp(new Action());
		}
		return false;
	}
	public boolean isWalkableUpL(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableUpL(i);
		tryMoveUp(new Action());
		return false;
	}
	public boolean isWalkableUpR(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableUpR(i);
		tryMoveUp(new Action());
		return false;
	}
	
	public boolean canPushDownL(Interactor i)
	{
		return check(0, 1) && ((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).canPushDownL(i);
	}
	public boolean canPushDownR(Interactor i)
	{
		return check(0, 1) && ((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).canPushDownR(i);
	}
	public boolean isWalkableDown(Interactor i)
	{
		if(canPushDownL(i) && canPushDownR(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableDownL(i);
			((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableDownR(i);
			tryMoveDown(new Action());
		}
		return false;
	}
	public boolean isWalkableDownL(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableDownL(i);
		tryMoveDown(new Action());
		return false;
	}
	public boolean isWalkableDownR(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableDownR(i);
		tryMoveDown(new Action());
		return false;
	}
	
	
	
	public boolean canPushLeftU(Interactor i)
	{
		return check(-1, 0) && ((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).canPushLeftU(i);
	}
	public boolean canPushLeftD(Interactor i)
	{
		return check(-1, 0) && ((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).canPushLeftD(i);
	}
	public boolean isWalkableLeft(Interactor i)
	{
		if(canPushLeftU(i) && canPushLeftD(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableLeftU(i);
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableLeftD(i);
			tryMoveLeft(new Action());
		}
		return false;
	}
	public boolean isWalkableLeftU(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableLeftU(i);
		tryMoveLeft(new Action());
		return false;
	}
	public boolean isWalkableLeftD(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableLeftD(i);
		tryMoveLeft(new Action());
		return false;
	}
	
	
	public boolean canPushRightU(Interactor i)
	{
		return check(1, 0) && ((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).canPushRightU(i);
	}
	public boolean canPushRightD(Interactor i)
	{
		return check(1, 0) && ((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).canPushRightD(i);
	}
	public boolean isWalkableRight(Interactor i)
	{
		if(canPushRightU(i) && canPushRightD(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableRightU(i);
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableRightD(i);
			tryMoveRight(new Action());
		}
		return false;
	}
	public boolean isWalkableRightU(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableRightU(i);
		tryMoveRight(new Action());
		return false;
	}
	public boolean isWalkableRightD(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableRightD(i);
		tryMoveRight(new Action());
		return false;
	}
	
	boolean check(int dx, int dy)
	{
		return GameWorld.getWorld().getInteractable(getX()+dx, getY()+dy) == null && GameWorld.getWorld().getWalkable(getX()+dx, getY()+dy) != null;
	}
}
