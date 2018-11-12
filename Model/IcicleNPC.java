import java.util.HashSet;
public class IcicleNPC extends Charact
{
	public IcicleNPC()
	{
		setSprite("left-icicle.png");
	}
	public IcicleNPC makeCopy()
	{
		return new IcicleNPC();
	}
	public String getClassName()
	{
		return "IcicleNPC";
	}
	public String parse(String saved)
	{
		saved = super.parse(saved);
		if(GameWorld.getWorld().getPlayer() != null && !Memory.found.contains("ice.mp4"))
			throw new DoNotCreateException();
		return saved;
	}
}
