public abstract class Interactor extends Interactable
{
	public void tryMoveUp(Action then)
	{
		tryMove(0, -1, then, (w, i)->w.isWalkableUp(i), (w, i)->w.onWalkUp(i), (w, i)->w.onWalkOffUp(i));
	}
	public void tryMoveDown(Action then)
	{
		tryMove(0, 1, then, (w, i)->w.isWalkableDown(i), (w, i)->w.onWalkDown(i), (w, i)->w.onWalkOffDown(i));
	}
	public void tryMoveLeft(Action then)
	{
		tryMove(-1, 0, then, (w, i)->w.isWalkableLeft(i), (w, i)->w.onWalkLeft(i), (w, i)->w.onWalkOffLeft(i));
	}
	public void tryMoveRight(Action then)
	{
		tryMove(1, 0, then, (w, i)->w.isWalkableRight(i), (w, i)->w.onWalkRight(i), (w, i)->w.onWalkOffRight(i));
	}
	public void tryMove(int x, int y, Action then, DirectionChecker check, DirectionActor on, DirectionActor off)
	{
		double xc = getX();
		double yc = getY();
		Interactable interactable = GameWorld.getWorld().getInteractable(xc + x, yc + y);
		if(interactable != null)
		{
			then.start();
			return;
		}
		Walkable target = GameWorld.getWorld().getWalkable(xc + x, yc + y);
		if(target == null || !check.check(target, this))
		{
			then.start();
			return;
		}
		Walkable floor = getFloor();
		if(floor != null) off.act(floor, this);
		move(x, y, new Action(o->{on.act(getFloor(), this);o.start();}).then(o->{then.start(); o.start();}), target);
	}
	public static interface DirectionChecker
	{
		public boolean check(Walkable w, Interactor i);
	}
	public static interface DirectionActor
	{
		public void act(Walkable w, Interactor i);
	}
}
