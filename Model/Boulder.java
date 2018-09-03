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
		System.out.println(i);
		tryMoveUp(new Action());
		return false;
	}
	public boolean isWalkableDown(Interactor i)
	{
		tryMoveDown(new Action());
		return false;
	}
	public boolean isWalkableLeft(Interactor i)
	{
		tryMoveLeft(new Action());
		return false;
	}
	public boolean isWalkableRight(Interactor i)
	{
		tryMoveRight(new Action());
		return false;
	}
	public void setY(double y, boolean b)
	{
		System.out.println(y);
		super.setY(y, b);
	}
}
