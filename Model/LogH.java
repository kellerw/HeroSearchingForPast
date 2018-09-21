public class LogH extends Log
{
	public LogH()
	{
		setSprite("logh.png");
	}
	public LogH makeCopy()
	{
		return new LogH();
	}
	public String getClassName()
	{
		return "Log-Horizontal";
	}
	public boolean isWalkableLeft(Interactor i)
	{
		return false;
	}
	public boolean isWalkableRight(Interactor i)
	{
		return false;
	}
}
