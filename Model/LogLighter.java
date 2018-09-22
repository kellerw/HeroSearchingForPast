public class LogLighter extends Log
{
	public LogLighter()
	{
		setSprite("loglighter.png");
	}
	public LogLighter makeCopy()
	{
		return new LogLighter();
	}
	public String getClassName()
	{
		return "Log-Lighter";
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
	public void onInteract(Interactor i)
	{
		setOnFire();
	}
}
