public class Stump extends Log
{
	public Stump()
	{
		setSprite("stump.png");
	}
	public Stump makeCopy()
	{
		return new Stump();
	}
	public String getClassName()
	{
		return "Log-Stump";
	}
	public boolean isWalkableLeft(Interactor i)
	{
		return false;
	}
	public boolean isWalkableRight(Interactor i)
	{
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
	public void setOnFire()
	{
		setOnFire(new Action((o)->{GameWorld.getWorld().remove(this);GameWorld.getWorld().remove(getFlame()); o.start();}));
	}
}
