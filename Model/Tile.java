public abstract class Tile extends Movable
{
	private Base base;
	public double getX()
	{
		return base.getX();
	}
	public double getY()
	{
		return base.getY();
	}
	public boolean isTile()
	{
		return true;
	}
}
