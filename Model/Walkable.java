public abstract class Walkable extends GameObject
{
	//called when interactor tries to walk into this
	public boolean isWalkable(Interactor i)
	{
		return true;
	}
	public boolean isWalkableUp(Interactor i)
	{
		return isWalkable(i);
	}
	public boolean isWalkableDown(Interactor i)
	{
		return isWalkable(i);
	}
	public boolean isWalkableLeft(Interactor i)
	{
		return isWalkable(i);
	}
	public boolean isWalkableRight(Interactor i)
	{
		return isWalkable(i);
	}
	//called when interactor walks onto this
	public Action onWalk(Interactor i)
	{
		return new Action();
	}
	public Action onWalkUp(Interactor i)
	{
		return onWalk(i);
	}
	public Action onWalkDown(Interactor i)
	{
		return onWalk(i);
	}
	public Action onWalkLeft(Interactor i)
	{
		return onWalk(i);
	}
	public Action onWalkRight(Interactor i)
	{
		return onWalk(i);
	}
	//called when interactor walks off of this
	public Action onWalkOff(Interactor i)
	{
		return new Action();
	}
	public Action onWalkOffUp(Interactor i)
	{
		return onWalkOff(i);
	}
	public Action onWalkOffDown(Interactor i)
	{
		return onWalkOff(i);
	}
	public Action onWalkOffLeft(Interactor i)
	{
		return onWalkOff(i);
	}
	public Action onWalkOffRight(Interactor i)
	{
		return onWalkOff(i);
	}
}
