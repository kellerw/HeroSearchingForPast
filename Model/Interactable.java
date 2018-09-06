public abstract class Interactable extends Movable
{
	private Walkable tile;
	public double getX()
	{
		return tile==null?super.getX():tile.getX();
	}
	public double getY()
	{
		return tile==null?super.getY():tile.getY();
	}
	public void setX(double x, boolean b)
	{
		int ox = (int)getX();
		tile = GameWorld.getWorld().getWalkable(x, getY());
		super.setX(x, b);
		GameWorld.getWorld().moveInteractable(this, ox, (int)getY(), (int)getX(), (int)getY());
	}
	public void setY(double y, boolean b)
	{
		int oy = (int)getY();
		tile = GameWorld.getWorld().getWalkable(getX(), y);
		super.setY(y, b);
		GameWorld.getWorld().moveInteractable(this, (int)getX(), oy, (int)getX(), (int)getY());
	}
	//called when interactor interacts with this
	public void onInteract(Interactor i)
	{
	}
	public void onInteractUp(Interactor i)
	{
		onInteract(i);
	}
	public void onInteractDown(Interactor i)
	{
		onInteract(i);
	}
	public void onInteractLeft(Interactor i)
	{
		onInteract(i);
	}
	public void onInteractRight(Interactor i)
	{
		onInteract(i);
	}
	public Walkable getFloor()
	{
		if(tile == null)
			return super.getFloor();
		return tile;
	}
	public boolean isInteractable()
	{
		return true;
	}
}
