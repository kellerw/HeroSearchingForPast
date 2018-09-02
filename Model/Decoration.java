public class Decoration extends GameObject
{
	//whether to draw it above the player or below
	private boolean isTopLayer = false;
	private String image;
	public Decoration(String image)
	{
		setSprite(image);
	}
	public Decoration makeCopy()
	{
		return new Decoration(getImageName());
	}
	
	public boolean isTopLayer()
	{
		return isTopLayer;
	}
	public void setIsTopLayer(boolean topLayer)
	{
		this.isTopLayer = topLayer;
	}
	public String getClassName()
	{
		return "Decoration";
	}
	public String parse(String saved)
	{
		saved = super.parse(saved);
		int index = saved.indexOf("\t\t");
		String s = saved.substring(0, index);
		setIsTopLayer(s.equals("true"));
		return saved.substring(index+2);
	}
	public String toString()
	{
		return super.toString() + isTopLayer() + "\t\t";
	}
	public boolean isDecoration()
	{
		return true;
	}
}
