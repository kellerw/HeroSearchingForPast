public abstract class Walkable extends GameObject
{
	//called when interactor tries to walk into this
	public boolean isWalkable(Interactor i)
	{
		return false;
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
	public void onWalk(Interactor i)
	{
	}
	public void onWalkUp(Interactor i)
	{
		onWalk(i);
	}
	public void onWalkDown(Interactor i)
	{
		onWalk(i);
	}
	public void onWalkLeft(Interactor i)
	{
		onWalk(i);
	}
	public void onWalkRight(Interactor i)
	{
		onWalk(i);
	}
	//called when interactor walks off of this
	public void onWalkOff(Interactor i)
	{
	}
	public void onWalkOffUp(Interactor i)
	{
		onWalkOff(i);
	}
	public void onWalkOffDown(Interactor i)
	{
		onWalkOff(i);
	}
	public void onWalkOffLeft(Interactor i)
	{
		onWalkOff(i);
	}
	public void onWalkOffRight(Interactor i)
	{
		onWalkOff(i);
	}
}
