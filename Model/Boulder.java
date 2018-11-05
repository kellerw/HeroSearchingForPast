public class Boulder extends Interactor
{
	public Boulder()
	{
		setSprite("boulder.png");
	}
	public Boulder makeCopy()
	{
		return new Boulder();
	}
	public String getClassName()
	{
		return "Boulder";
	}
	public boolean isWalkableUp(Interactor i)
	{
		GameWorld.getWorld().playSound("push.mp3", new Action());
		tryMoveUp(new Action());
		return false;
	}
	public boolean isWalkableDown(Interactor i)
	{
		GameWorld.getWorld().playSound("push.mp3", new Action());
		tryMoveDown(new Action());
		return false;
	}
	public boolean isWalkableLeft(Interactor i)
	{
		GameWorld.getWorld().playSound("push.mp3", new Action());
		tryMoveLeft(new Action());
		return false;
	}
	public boolean isWalkableRight(Interactor i)
	{
		GameWorld.getWorld().playSound("push.mp3", new Action());
		tryMoveRight(new Action());
		return false;
	}
}
