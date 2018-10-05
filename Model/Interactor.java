public abstract class Interactor extends Interactable
{
	private int lastx = 0;
	private int lasty = 1;
	public int getLastX()
	{
		return lastx;
	}
	public int getLastY()
	{
		return lasty;
	}
	public void interact()
	{
		Interactable i = GameWorld.getWorld().getInteractable(getX() + getLastX(), getY() + getLastY());
		if(i == null)
			return;
		else if(lastx == 1)
			i.onInteractRight(this);
		else if(lastx == -1)
			i.onInteractLeft(this);
		else if(lasty == 1)
			i.onInteractDown(this);
		else if(lasty == -1)
			i.onInteractUp(this);
	}
	public void tryMoveUp(Action then)
	{
		lastx = 0;
		lasty = -1;
		tryMove(0, -1, then, (w, i)->w.isWalkableUp(i), (w, i)->w.onWalkUp(i), (w, i)->w.onWalkOffUp(i));
	}
	public void tryMoveDown(Action then)
	{
		lastx = 0;
		lasty = 1;
		tryMove(0, 1, then, (w, i)->w.isWalkableDown(i), (w, i)->w.onWalkDown(i), (w, i)->w.onWalkOffDown(i));
	}
	public void tryMoveLeft(Action then)
	{
		lasty = 0;
		lastx = -1;
		tryMove(-1, 0, then, (w, i)->w.isWalkableLeft(i), (w, i)->w.onWalkLeft(i), (w, i)->w.onWalkOffLeft(i));
	}
	public void tryMoveRight(Action then)
	{
		lasty = 0;
		lastx = 1;
		tryMove(1, 0, then, (w, i)->w.isWalkableRight(i), (w, i)->w.onWalkRight(i), (w, i)->w.onWalkOffRight(i));
	}
	public void tryMove(int x, int y, Action then, DirectionChecker check, DirectionActor on, DirectionActor off)
	{
		double xc = getX();
		double yc = getY();
		Interactable interactable = GameWorld.getWorld().getInteractable(xc + x, yc + y);
		if(interactable != null)
		{
			check.check(interactable, this);
			then.start();
			return;
		}
		Walkable target = GameWorld.getWorld().getWalkable(xc + x, yc + y);
		Walkable floor = getFloor();
		if(target == null || !check.check(target, this))
		{
			then.start();
			return;
		}
		if(floor != null) off.act(floor, this).start();
		move(x, y, on.act(target, this).then(then), target);
	}
	public static interface DirectionChecker
	{
		public boolean check(Walkable w, Interactor i);
	}
	public static interface DirectionActor
	{
		public Action act(Walkable w, Interactor i);
	}
}
