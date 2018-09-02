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
	public void setX(double x)
	{
		tile = GameWorld.getWorld().getWalkable(x, getY());
		super.setX(x);
	}
	public void setY(double y)
	{
		tile = GameWorld.getWorld().getWalkable(getX(), y);
		super.setY(y);
	}
	//called when interactor walks onto this
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
