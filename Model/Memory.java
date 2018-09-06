import java.util.HashSet;
public class Memory extends Interactable
{
	private static HashSet<String> found = new HashSet<>();
	public Memory()
	{
		setSprite("bubble.png");
	}
	public Memory makeCopy()
	{
		return new Memory();
	}
	public String getClassName()
	{
		return "Memory";
	}
	public void onInteract(Interactor i)
	{
		found.add(getNames());
		GameWorld.getWorld().remove(this);
		GameWorld.getWorld().showCutscene(getNames());
	}
	public String parse(String saved)
	{
		saved = super.parse(saved);
		if(found.contains(getNames()))
			throw new DoNotCreateException();
		return saved;
	}
}
