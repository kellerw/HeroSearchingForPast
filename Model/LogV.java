public class LogV extends Log
{
	public LogV()
	{
		setSprite("logv.png");
	}
	public LogV makeCopy()
	{
		return new LogV();
	}
	public String getClassName()
	{
		return "Log-Vertical";
	}
	public boolean isWalkableUp(Interactor i)
	{
		return false;
	}
	public boolean isWalkableDown(Interactor i)
	{
		return false;
	}
}
