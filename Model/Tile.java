public abstract class Tile extends Movable
{
	private Base base;
	public double getX()
	{
		if(base == null)
			return super.getX();
		return base.getX();
	}
	public double getY()
	{
		if(base == null)
			return super.getY();
		return base.getY();
	}
	public boolean isTile()
	{
		return true;
	}
}
