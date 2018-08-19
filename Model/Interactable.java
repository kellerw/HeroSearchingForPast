public abstract class Interactable extends Movable
{
	private Tile tile;
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
}
